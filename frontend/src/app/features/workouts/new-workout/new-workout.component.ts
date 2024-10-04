import {Component, OnDestroy, OnInit} from '@angular/core';
import {
  EjercicioRes,
  EntrenoControllerService,
  EntrenoReq,
  ItemRutinaControllerService,
  ItemRutinaRes,
  RutinaControllerService,
  RutinaRes,
  SerieReq
} from "../../../core/services/api-client";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {concatMap} from "rxjs";
import {AbstractControl, FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import TipoDeEjercicioEnum = EjercicioRes.TipoDeEjercicioEnum;

@Component({
  selector: 'app-new-workout',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    ReactiveFormsModule,
    NgForOf,
    NgClass
  ],
  templateUrl: './new-workout.component.html',
  styleUrl: './new-workout.component.css'
})
export class NewWorkoutComponent implements OnInit, OnDestroy{
  timerRef : any;
  hours: number = 0;
  minutes: number = 0;
  seconds : number = 0;

  workout!: EntrenoReq;
  routine!:RutinaRes;
  items!: ItemRutinaRes[];
  form!: FormGroup;

  constructor(
    private _entrenoService: EntrenoControllerService,
    private _rutinaService : RutinaControllerService,
    private _itemService : ItemRutinaControllerService,
    private _route: ActivatedRoute,
    private _fb: FormBuilder,
    private _router: Router
  ) {}


  ngOnInit(): void {
    this.startTimer();
    this._rutinaService.getRutinaById(this._route.snapshot.queryParams['routineId']).pipe(
      concatMap(routine => {
        this.routine = routine;
        return this._itemService.getItems(routine.id!);
        })).subscribe({
        next: items => {
          this.items = items;
          this.form = this._fb.group({
            items: this.createItemControls()
          });
        }
      });
  }

  createItemControls(): FormArray{
    return this._fb.array(this.items!.map(item => {
      return this._fb.group({
        id: [item.id],
        ejercicio: this._fb.group({
          id: item.ejercicio?.id,
          nombre: item.ejercicio?.nombre,
          tipoDeEjercicio: item.ejercicio?.tipoDeEjercicio
        }),
        descansoEnSeg: [item.descansoEnSeg, [Validators.min(1), Validators.required]],
        nota: [item.nota],
        series: this.createSetControls(item)
      })
    }));
  }

  createSetControls(item: ItemRutinaRes) : FormArray{
    return this._fb.array(item.series!.map(set => {
      return this._fb.group({
        id: [set.id],
        reps: [set.reps, Validators.min(1)],
        pesoEnKg: [set.pesoEnKg, Validators.min(1)],
        tiempoEnSeg: [set.tiempoEnSeg, Validators.min(1)],
        distancia: [set.distancia, Validators.min(1)],
        selected: [false],
      })
    }));
  }

  get itemsFormArray() : FormArray{
    // @ts-ignore
    return this.form!.get('items') as FormArray;
  }

  setsFormArray(item: AbstractControl):FormArray{
    // @ts-ignore
    return item.get('series') as FormArray;
  }


  addSet(index: number){
    let item = (this.form.get('items') as FormArray).at(index);
    let series = item.get('series') as FormArray;
    let ejercicio = item.value.ejercicio;
    let serieAnt = series.at(series.length - 1);
    series.push(this._fb.group({
      id: undefined,
      pesoEnKg: this.appropriateExerciseType(ejercicio!, 'weigh')? (serieAnt? serieAnt.get('pesoEnKg')!.value : 0) : undefined,
      reps: this.appropriateExerciseType(ejercicio!, 'reps') ? (serieAnt? serieAnt.get('reps')!.value : 0) : undefined,
      distancia: this.appropriateExerciseType(ejercicio!, 'distance') ? (serieAnt? serieAnt.get('distancia')!.value : 0)  : undefined,
      tiempoEnSeg: this.appropriateExerciseType(ejercicio!, 'time') ? (serieAnt? serieAnt.get('tiempoEnSeg')!.value : 0)  : undefined,
      selected: false
    }));

  }



  saveWorkout() {
    console.log(this.form.get('items'));

    let workout: EntrenoReq = {
      rutinaId: this.routine.id!,
      duracionEnSeg: this.hours * 3600 + this.minutes * 60 + this.seconds,
      fecha: new Date().toISOString(),
      items: []
    };

    let items = this.form.get('items') as FormArray;

    // Filtras solo los items que tienen al menos una serie seleccionada
    let validItems = items.controls.filter(item =>
      item.value.series.some((serie: any) => serie.selected)
    );

    workout.items = validItems.map((item) => {
      // Filtra las series seleccionadas en cada item
      let filteredSeries = item.value.series.filter((serie: any) => serie.selected);

      return {
        ejercicioId: item.get('ejercicio')!.get('id')!.value,
        descansoEnSeg: item.get('descansoEnSeg')!.value,
        nota: item.get('nota')!.value,
        // Asigna solo las series seleccionadas
        series: filteredSeries.map((serie: any) => {
          return {
            reps: serie.reps,
            pesoEnKg: serie.pesoEnKg,
            tiempoEnSeg: serie.tiempoEnSeg,
            distancia: serie.distancia
          };
        }) as SerieReq[]
      };
    });

    console.log(workout);

    this._entrenoService.nuevoEntreno(workout).subscribe({
      next: (updatedItem) => {
        console.log(updatedItem);
        this._router.navigate(['workouts'])
      }
    });


  }

  discardWorkout(){
    this._router.navigate(['routines']);
  }

  removeItem(index:number){
    (this.form.get('items') as FormArray).removeAt(index);
  }

  appropriateExerciseType(typeOfExercise: EjercicioRes, type: string) {
    const exerciseType: TipoDeEjercicioEnum = typeOfExercise.tipoDeEjercicio!;
    switch (type) {
      case 'reps': return ['PESO_Y_REPETICIONES','PESO_CORPORAL','PESO_CORPORAL_CON_PESO_EXTRA','PESO_CORPORAL_ASISTIDO'].includes(exerciseType!);
      case 'distance' : return ['DISTANCIA_Y_PESO'].includes(exerciseType!);
      case 'time' : return ['DURACION','DISTANCIA_Y_DURACION'].includes(exerciseType!);
      case 'weigh' : return ['PESO_Y_REPETICIONES', 'PESO_CORPORAL_CON_PESO_EXTRA'].includes(exerciseType!);
      default: return false;
    }
  }

  startTimer(){
    const startTime = Date.now();
    let counter;
    this.timerRef = setInterval(()=>{
      counter = Math.floor((Date.now()-startTime)/1000);
      this.hours = Math.floor((counter!/3600)%60);
      this.minutes = Math.floor((counter!/60)%60);
      this.seconds = Math.floor(counter!%60);
    },1000);
  }

  ngOnDestroy(): void {
    clearInterval(this.timerRef);
  }

  protected readonly FormArray = FormArray;
}
