import {Component, OnDestroy, OnInit} from '@angular/core';
import {
  EntrenoControllerService,
  EntrenoRes, ItemRutinaControllerService, ItemRutinaRes,
  RutinaControllerService,
  RutinaRes
} from "../../../core/services/api-client";
import {NgIf} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {concatMap} from "rxjs";

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

  workout!: EntrenoRes;
  routine!:RutinaRes;
  items: ItemRutinaRes[] = [];


  constructor(
    private _entrenoService: EntrenoControllerService,
    private _rutinaService : RutinaControllerService,
    private _itemService : ItemRutinaControllerService,
    private _route: ActivatedRoute
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
          console.log(items);
        }
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
