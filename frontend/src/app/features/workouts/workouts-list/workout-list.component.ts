import {Component, OnInit} from '@angular/core';
import {DatePipe, NgForOf, NgIf, UpperCasePipe} from "@angular/common";
import {ItemModalComponent} from "../../../shared/item-modal/item-modal.component";
import {BaseChartDirective} from "ng2-charts";
import {ChartComponent} from "../../../shared/chart/chart.component";
import {EjercicioReq, EntrenoControllerService, EntrenoRes} from "../../../core/services/api-client";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {WorkoutDetailComponent} from "../workout-detail/workout-detail.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import GrupoMuscularPrimarioEnum = EjercicioReq.GrupoMuscularPrimarioEnum;

@Component({
  selector: 'app-workouts',
  standalone: true,
  imports: [
    NgIf,
    ItemModalComponent,
    BaseChartDirective,
    ChartComponent,
    NgForOf,
    UpperCasePipe,
    DatePipe,
    FaIconComponent,
    WorkoutDetailComponent
],
  templateUrl: './workout-list.component.html',
  styleUrl: './workout-list.component.css'
})
export class WorkoutListComponent implements OnInit{
  workouts!:EntrenoRes[];


  constructor(
    private _workoutService: EntrenoControllerService,
    private _modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.getWorkouts();
  }

  getWorkouts(){
    this._workoutService.listarEntrenos().subscribe({
      next: workouts => this.workouts = workouts
    });
  }

  open(workout: EntrenoRes){
    const muscleGroupsOccurrence =  this.calculateMuscleGroupOccurrence(workout);

    const modalRef = this._modalService.open(WorkoutDetailComponent,{ scrollable: true });
    modalRef.componentInstance.workout = workout;
    modalRef.componentInstance.labels = Object.keys(muscleGroupsOccurrence);
    modalRef.componentInstance.data = Object.values(muscleGroupsOccurrence);
  }

  calculateMuscleGroupOccurrence(workout: EntrenoRes){
    const muscleGroupsOccurrence : {[key:string]:number} = {}
    if (workout.items) {
      const primaryMuscleGroups = workout.items.map(i => i.ejercicio?.grupoMuscularPrimario).filter((m): m is GrupoMuscularPrimarioEnum => m !== undefined);
      const secondaryMuscleGroups = workout.items.map(i => i.ejercicio?.grupoMuscularSecundario).filter((m): m is GrupoMuscularPrimarioEnum => m !== undefined);
      if (primaryMuscleGroups){
        primaryMuscleGroups.forEach((m : GrupoMuscularPrimarioEnum) => {
          muscleGroupsOccurrence[m] = (muscleGroupsOccurrence[m]  || 0 ) + 1;
        })
      }
      if (secondaryMuscleGroups){
        secondaryMuscleGroups.forEach((m : GrupoMuscularPrimarioEnum) => {
          muscleGroupsOccurrence[m] = (muscleGroupsOccurrence[m]  || 0 ) + 0.3;
        })
      }
    }
    return muscleGroupsOccurrence;
  }




}
