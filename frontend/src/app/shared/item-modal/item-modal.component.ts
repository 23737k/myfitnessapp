import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {
  EjercicioControllerService,
  EjercicioRes,
  ItemRutinaReq,
  ItemRutinaRes,
  SerieReq
} from "../../core/services/api-client";
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import TipoDeEjercicioEnum = EjercicioRes.TipoDeEjercicioEnum;

@Component({
  selector: 'app-item-modal',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    NgForOf,
    NgClass
  ],
  templateUrl: './item-modal.component.html',
  styleUrl: './item-modal.component.css'
})
export class ItemModalComponent implements OnInit{

  @Input() item?:ItemRutinaRes;
  @Output() savedItem: EventEmitter<ItemRutinaReq> = new EventEmitter();

  form!:FormGroup;
  exercises?: EjercicioRes[];

  constructor(
    private _fb:FormBuilder,
    private _exerciseService: EjercicioControllerService
  ) {}

  ngOnInit(): void {
    this._exerciseService.listarEjercicios().subscribe({
      next: exercises => {
        this.exercises = exercises;
        this.createForm();
        this.form.get('sets')!.valueChanges.subscribe({
          next: ()=> this.checkSets()
        })
      }})
  }

  createForm() {
    this.form = this._fb.group({
      exerciseId: [this.item ? this.item.ejercicio?.id : 3, Validators.required],
      rest: [this.item ? this.item.descansoEnSeg : 60, [Validators.min(1), Validators.required]],
      note: [this.item ? this.item.nota : ''],
      sets: this._fb.array(
        this.item ? this.item.series!.map(s => {
          return this._fb.group({
            reps: [s.reps, [Validators.required, Validators.min(1)]],
            weighInKg: [s.pesoEnKg, [Validators.min(0)]],
            timeInSecs: [s.tiempoEnSeg, [Validators.min(0)]],
            distance: [s.distancia, [Validators.min(0)]]
          });
        }) : []
      )
    });
  }

  checkSets(){
    if(this.form.get('sets')!.value.some( (s : any) => !this.validSet(this.form.get('exerciseId')!.value, s))){
      this.form.get('sets')!.setErrors({
        invalidSets: {
          message: 'There are some invalid sets'
        }
      });
    }
    if(this.form.get('sets')!.value.length === 0){
      this.form.get('sets')!.setErrors({
        emptySets: {
          message: 'The sets array is empty'
        }
      })
    }
    console.log(this.form);
  }

  getFormErrors(): string[] {
    const errors: string[] = [];

    // Comprobar errores en los controles
    Object.keys(this.form.controls).forEach(controlName => {
      const control = this.form.get(controlName);
      if (control && control.errors) {
        Object.keys(control.errors).forEach(errorKey => {
          errors.push(`Error en: ${controlName}: ${errorKey}`);
        });
      }
    });
    return errors;
  }

  saveItem(){
    if(this.form.valid){
      let formValue = this.form.value;
      if(formValue.sets.length >= 0){
        let savedItem: ItemRutinaReq = {
          ejercicioId: formValue.exerciseId,
          descansoEnSeg: formValue.rest,
          nota: formValue.note,
          series: (formValue.sets) as Array<SerieReq>
        };
        this.savedItem.emit(savedItem);
      }
    }
  }

  validSet(exerciseId: number, set:any): boolean{
    return (
      (this.appropriateExerciseType(exerciseId, 'reps') ? set.reps > 0 : true) &&
      (this.appropriateExerciseType(exerciseId, 'weighInKg') ? set.weighInKg > 0 : true) &&
      (this.appropriateExerciseType(exerciseId, 'distance') ? set.distance > 0 : true) &&
      (this.appropriateExerciseType(exerciseId, 'timeInSecs') ? set.timeInSecs > 0 : true)
    );
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
      case 'timeInSecs' : return ['DURACION','DISTANCIA_Y_DURACION'].includes(exerciseType!);
      case 'weighInKg' : return ['PESO_Y_REPETICIONES', 'PESO_CORPORAL_CON_PESO_EXTRA'].includes(exerciseType!);
      default: return false;
    }
  }

  get formSets(): FormArray{
    return this.form?.get('sets') as FormArray
  }
}
