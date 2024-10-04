import {Component, Input, OnInit} from '@angular/core';
import {EjercicioControllerService, EjercicioRes, ItemRutinaRes, SerieRes} from "../../core/services/api-client";
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import TipoDeEjercicioEnum = EjercicioRes.TipoDeEjercicioEnum;

@Component({
  selector: 'app-item-modal',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgForOf
  ],
  templateUrl: './item-modal.component.html',
  styleUrl: './item-modal.component.css'
})
export class ItemModalComponent implements OnInit{

  @Input() item?:ItemRutinaRes;

  form!:FormGroup;
  exercises?: EjercicioRes[];

  constructor(
    private _fb:FormBuilder,
    private _exerciseService: EjercicioControllerService
  ) {}

  ngOnInit(): void {
    this.form = this._fb.group({
      exerciseId: [this.item? this.item.ejercicio?.id : 3, Validators.required],
      rest: [this.item? this.item.descansoEnSeg : 60, [Validators.min(1), Validators.required]],
      note: [this.item? this.item.nota : ''],
      sets: this.item? this._fb.array(this.item.series!.map(s => {
        return this._fb.group({
          reps: s.reps,
          weighInKg: s.pesoEnKg,
          timeInSecs: s.tiempoEnSeg,
          distance: s.distancia
        })
      })) : this._fb.array([])
    });
    console.log(this.item)
    this._exerciseService.listarEjercicios().subscribe({
      next: exercises => {
        this.exercises = exercises;
      }})
  }

  saveItem(){
    console.log(this.form.value);
  }

  removeSet(i:number){
    this.formSets.removeAt(i);
  }

  addNewSet(){
    // @ts-ignore
    this.formSets.push(this._fb.group({
      reps: null,
      weighInKg: null,
      timeInSecs: null,
      distance: null
    }))
  }

  appropriateExerciseType(exerciseId:number, type: string) {
// @ts-ignore
    let exercise = this.exercises?.find(e => e.id === exerciseId);
    const exerciseType: TipoDeEjercicioEnum = exercise!.tipoDeEjercicio!;
    switch (type) {
      case 'reps': return ['PESO_Y_REPETICIONES','PESO_CORPORAL','PESO_CORPORAL_CON_PESO_EXTRA','PESO_CORPORAL_ASISTIDO'].includes(exerciseType!);
      case 'distance' : return ['DISTANCIA_Y_PESO'].includes(exerciseType!);
      case 'time' : return ['DURACION','DISTANCIA_Y_DURACION'].includes(exerciseType!);
      case 'weigh' : return ['PESO_Y_REPETICIONES', 'PESO_CORPORAL_CON_PESO_EXTRA'].includes(exerciseType!);
      default: return false;
    }
  }

  get formSets(): FormArray{
    return this.form?.get('sets') as FormArray
  }
}
