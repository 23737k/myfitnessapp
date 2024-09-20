import {Component, OnInit} from '@angular/core';
import {EjercicioControllerService} from "../../core/services/api-client";

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

    this._exerciseService.listarEjercicios({}).subscribe({
      next: ejercicios => console.log(ejercicios.content?.at(1))
    })


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

  constructor(private _exerciseService: EjercicioControllerService) {
  }


}
