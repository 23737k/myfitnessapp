import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {RutinaControllerService, RutinaReq, RutinaRes} from "../../core/services/api-client";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {ChangeDetection} from "@angular/cli/lib/config/workspace-schema";

@Component({
  selector: 'app-routines',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './routines.component.html',
  styleUrl: './routines.component.css'
})
export class RoutinesComponent implements OnInit{
  routines!:RutinaRes[];
  routineForm!: FormGroup;
  loading: boolean = true;

  ngOnInit() {
    this._routineService.listarRutinas().subscribe({
      next: async rutinas => {
        this.loading = false;
        this.routines = rutinas;
      },
      error: err => {
        console.log("Error fetching routines");
        this.loading = false;
      }
    });
    this.routineForm = this._fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  constructor(private _routineService: RutinaControllerService, private _router: Router, private _fb: FormBuilder) {
  }

  seeDetails(id: number | undefined): void {
    this._router.navigateByUrl(`routines/${id}`);
  }

  startRoutine(event:Event, routineId: number){
    event.stopPropagation();
    this._router.navigate(['new-workout'], {queryParams: {routineId : routineId}})
  }

  createRoutine(){
    if(this.routineForm.valid){
      const newRoutine : RutinaReq = {
        nombre: this.routineForm.get('name')!.value,
        descripcion: this.routineForm.get('description')!.value,
      };

      this._routineService.crearRutina(newRoutine).subscribe({
        next: rutina => {
          this.routines.push(newRoutine);
          alert("Routine created successfully!");
          this._router.navigate([`routines/${rutina.id}`]);
        }
      });
    }
    else {
      alert("Some fields are missing");
    }
  }


  removeRoutine(routineId: number) {
    this._routineService.eliminarRutina(routineId).subscribe({
      next: value => this.routines = this.routines.filter(routine => routine.id !== routineId)
    });
  }
}
