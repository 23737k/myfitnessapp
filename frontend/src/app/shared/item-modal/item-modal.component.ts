import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges, OnDestroy,
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
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule, ValidationErrors,
  ValidatorFn,
  Validators
} from "@angular/forms";
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
  @Output() savedItem: EventEmitter<SavedItemEvent> = new EventEmitter();

  form!:FormGroup;
  exercises?: EjercicioRes[];

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
    // @ts-ignore
    this.form.get('sets')?.setValidators(this.setValidators(this.form.get('exerciseId').value));
  }


  setValidators = (exerciseId: number): ValidatorFn => {
    return (control: AbstractControl): ValidationErrors | null => {
      const sets = control.value;

      // Verifica si el array de sets está vacío
      if (sets.length === 0) {
        return { emptySets: true };
      }

      // Verifica si hay sets inválidos
      const invalidSets = sets.some((s: any) => {
        return !this.validSet(exerciseId, s);
      });

      if (invalidSets) {
        return { invalidSets: true };
      } else {
        return null;
      }
    };
  };

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
      let setsValue = this.form.get('sets')!.value;
      if(formValue.sets.length >= 0){
        let savedItem: ItemRutinaReq = {
          ejercicioId: formValue.exerciseId,
          descansoEnSeg: formValue.rest,
          nota: formValue.note,
          series: setsValue.map((s :any) => ({
            reps: s.reps,
            pesoEnKg: s.weighInKg,
            tiempoEnSeg: s.timeInSecs,
            distancia: s.distance
          }))
        };
        this.savedItem.emit({item: savedItem, id: this.item? this.item.id : undefined});
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
      this.createForm();
    }
  }

}

export interface SavedItemEvent {
  item: ItemRutinaReq;
  id: number | undefined;
}
