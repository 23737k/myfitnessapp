import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges
} from '@angular/core';
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
import {Subscription} from "rxjs";

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
export class ItemModalComponent implements OnInit, OnChanges{

  @Input() item?:ItemRutinaRes;
  @Output() savedItem: EventEmitter<ItemRutinaReq> = new EventEmitter();

  form!:FormGroup;
  exercises?: EjercicioRes[];
  formSetsChanges!: Subscription;

  constructor(
    private _fb:FormBuilder,
    private _exerciseService: EjercicioControllerService,
    private _cdref: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this._exerciseService.listarEjercicios().subscribe({
      next: exercises => {
        this.exercises = exercises;
        this.createForm();
        this.formSetsChanges =  this.form.get('sets')!.valueChanges!.subscribe({
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

  checkSets() {
    let sets = this.form.get('sets')!.value;

    // Comprobar si hay sets inválidos
    if (sets.some((s: any) => !this.validSet(this.form.get('exerciseId')!.value, s))) {
      this.form!.setErrors({
        invalidSets: {
          message: 'There are some invalid sets'
        }
      });
      console.log("hello")
    }
    else if (sets.length === 0) {
      // Comprobar si el array de sets está vacío
      this.form.get('sets')!.setErrors({
        emptySets: {
          message: 'The sets array is empty'
        }
      });

    } else {
      // Si no hay ningún error, eliminar los errores
      console.log("hello")
      this.form.get('sets')!.setErrors(null);
    }

    console.log(this.form)
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

  addNewSet() {
    const newSet = this._fb.group({
      reps: null,
      weighInKg: null,
      timeInSecs: null,
      distance: null
    });
    this.formSets.push(newSet);
    newSet.markAsTouched(); // Marcar los campos como tocados
    this.form.updateValueAndValidity(); // Actualizar los errores
    this.checkSets(); // Comprobar los sets después de agregar uno nuevo
    this._cdref.detectChanges();
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

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['item']) {
      if(this.formSetsChanges)
        this.formSetsChanges.unsubscribe();
      this.createForm();
      this.formSetsChanges =  this.form.get('sets')!.valueChanges!.subscribe({
        next: ()=> this.checkSets()
      });
    }
  }

}
