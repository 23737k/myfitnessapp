import { Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {
  EjercicioControllerService,
  EjercicioRes,
  ItemRutinaControllerService,
  ItemRutinaRes,
  RutinaControllerService,
  RutinaRes
} from "../../core/services/api-client";
import {NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import 'bootstrap';
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-routine-details',
  standalone: true,
  imports: [
    NgIf,
    FormsModule
  ],
  templateUrl: './routine-details.component.html',
  styleUrl: './routine-details.component.css'
})
export class RoutineDetailsComponent implements OnInit {

  rutina!: RutinaRes;
  itemsRutina!: ItemRutinaRes[];
  routineId!: number;
  editingItem!: ItemRutinaRes;
  exercises!: EjercicioRes[] ;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _rutinaService: RutinaControllerService,
    private _itemRutinaService: ItemRutinaControllerService,
    private _ejercicioService : EjercicioControllerService
  ) {}

  ngOnInit(): void {
    this.routineId = this._activatedRoute.snapshot.params['id'];

    // Usar forkJoin para esperar a que todas las llamadas se completen
    forkJoin({
      items: this._itemRutinaService.getItems(this.routineId!),
      rutina: this._rutinaService.getRutinaById(this.routineId!),
      ejercicios: this._ejercicioService.listarEjercicios()
    }).subscribe({
      next: ({ items, rutina, ejercicios }) => {
        this.itemsRutina = items;
        this.rutina = rutina;
        this.exercises = ejercicios;
      },
      error: err => console.error('Error al cargar datos:', err)
    });
  }

  totalSeries(){
    return this.itemsRutina!.reduce((total: number, item: ItemRutinaRes) => total + item!.series!.length, 0);

  }

  setItemToUpdate(id: number) {
    const item = this.itemsRutina.find(item => item.id === id);
    if (item) {
      this.editingItem = {
        ...item,
        series: JSON.parse(JSON.stringify(item.series)) // Copia profunda de series
      };
    }
    const modalElement = document.getElementById('editItemModal');
    // @ts-ignore
    const modal = new bootstrap.Modal(modalElement!);
    modal.show();
  }

  appopiateExerciseType(exercise: EjercicioRes | undefined, type: string) {
    // @ts-ignore
    const exerciseType = exercise.tipoDeEjercicio;
    switch (type) {
      case 'reps': return ['PESO_Y_REPETICIONES','PESO_CORPORAL','PESO_CORPORAL_CON_PESO_EXTRA','PESO_CORPORAL_ASISTIDO'].includes(exerciseType!);
      case 'distance' : return ['DISTANCIA_Y_PESO'].includes(exerciseType!);
      case 'time' : return ['DURACION','DISTANCIA_Y_DURACION'].includes(exerciseType!);
      case 'weigh' : return ['PESO_Y_REPETICIONES', 'PESO_CORPORAL_CON_PESO_EXTRA'].includes(exerciseType!);
      default: return false;
    }
  }

}
