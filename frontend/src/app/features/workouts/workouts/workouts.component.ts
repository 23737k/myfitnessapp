import {Component} from '@angular/core';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-workouts',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './workouts.component.html',
  styleUrl: './workouts.component.css'
})
export class WorkoutsComponent{

}
