import {Component, OnInit} from '@angular/core';
import {RutinaControllerService, RutinaRes} from "../../core/services/api-client";
import {Router} from "@angular/router";

@Component({
  selector: 'app-routines',
  standalone: true,
  imports: [],
  templateUrl: './routines.component.html',
  styleUrl: './routines.component.css'
})
export class RoutinesComponent implements OnInit{
  routines!:RutinaRes[];

  ngOnInit(): void {
    this._routineService.listarRutinas().subscribe({
      next: rutinas => this.routines = rutinas
    })
  }

  constructor(private _routineService: RutinaControllerService, private _router: Router) {
  }

  seeDetails(id: number | undefined): void {
    this._router.navigateByUrl(`routines/${id}`);
  }

  startRoutine(event:Event){
    event.stopPropagation();
  }

}
