import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-routines',
  standalone: true,
  imports: [],
  templateUrl: './routines.component.html',
  styleUrl: './routines.component.css'
})
export class RoutinesComponent implements OnInit{
  routines?:any[];

  ngOnInit(): void {
    this.routines = [
      {
        title: 'Legs',
        description: 'quads, calves, glutes and hamstrings'
      },
      {
        title: 'Push',
        description: 'Chest, Triceps and Shoulders'
      },
      {
        title: 'Pull',
        description: 'Back, Biceps and Forearms'
      }
    ]
  }


}
