import {Component, OnDestroy, OnInit} from '@angular/core';
import {
  EjercicioRes,
  EntrenoControllerService, EntrenoReq,
  ItemRutinaControllerService, ItemRutinaRes,
  RutinaControllerService,
  RutinaRes
} from "../../../core/services/api-client";
import {NgIf} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {concatMap} from "rxjs";
import TipoDeEjercicioEnum = EjercicioRes.TipoDeEjercicioEnum;
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-new-workout',
  standalone: true,
  imports: [
    NgIf
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


  createItemControls(): FormArray{
    return this._fb.array(this.items!.map(item => {
      return this._fb.group({
        id: [item.id],
        ejercicio: [item.ejercicio, [Validators.min(1), Validators.required]],
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


  constructor(
    private _entrenoService: EntrenoControllerService,
    private _rutinaService : RutinaControllerService,
    private _itemService : ItemRutinaControllerService,
    private _route: ActivatedRoute,
    private _fb: FormBuilder
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
            //TODO modificar algunos params absurdos en EntrenoReq
            items: this.createItemControls()
          });
        }
      });
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


  //TODO cambiar por formGroup
  addSet(item: ItemRutinaRes){
    item!.series!.push({
      pesoEnKg: this.appropriateExerciseType(item.ejercicio!, 'weigh')? 0 : undefined,
      reps: this.appropriateExerciseType(item.ejercicio!, 'reps') ? 0 : undefined,
      distancia: this.appropriateExerciseType(item.ejercicio!, 'distance') ? 0 : undefined,
      tiempoEnSeg: this.appropriateExerciseType(item.ejercicio!, 'time') ? 0 : undefined
    });
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

}
