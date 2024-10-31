import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {DatePipe, NgForOf, NgIf, UpperCasePipe} from "@angular/common";
import {ItemModalComponent} from "../../../shared/item-modal/item-modal.component";
import {BaseChartDirective} from "ng2-charts";
import {PieChartComponent} from "../../../shared/chart/pie-chart/pie-chart.component";
import {EntrenoControllerService, EntrenoRes} from "../../../core/services/api-client";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import { WorkoutDetailComponent } from "../workout-detail/workout-detail.component";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-workouts',
  standalone: true,
  imports: [
    NgIf,
    ItemModalComponent,
    BaseChartDirective,
    PieChartComponent,
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
    const modalRef = this._modalService.open(WorkoutDetailComponent);
    modalRef.componentInstance.workout = workout;
  }
  
  


}
