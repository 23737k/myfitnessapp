import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
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
import {concatMap, forkJoin} from "rxjs";
import {ItemModalComponent, SavedItemEvent} from "../../shared/item-modal/item-modal.component";
import TipoDeEjercicioEnum = EjercicioRes.TipoDeEjercicioEnum;

@Component({
  selector: 'app-routine-details',
  standalone: true,
  imports: [
    NgIf,
    FormsModule,
    RouterLink,
    ItemModalComponent
  ],
  templateUrl: './routine-details.component.html',
  styleUrl: './routine-details.component.css'
})
export class RoutineDetailsComponent implements OnInit {
  rutina!: RutinaRes;
  itemsRutina!: ItemRutinaRes[];
  routineId!: number;
  editingItem?: ItemRutinaRes;
  exercises!: EjercicioRes[];

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _rutinaService: RutinaControllerService,
    private _itemRutinaService: ItemRutinaControllerService,
    private _ejercicioService: EjercicioControllerService
  ) {
  }

  ngOnInit(): void {
    this.routineId = this._activatedRoute.snapshot.params['id'];

// Usar forkJoin para esperar a que todas las llamadas se completen
    forkJoin({
      items: this._itemRutinaService.getItems(this.routineId!),
      rutina: this._rutinaService.getRutinaById(this.routineId!),
      ejercicios: this._ejercicioService.listarEjercicios()
    }).subscribe({
      next: ({items, rutina, ejercicios}) => {
        this.itemsRutina = items;
        this.rutina = rutina;
        this.exercises = ejercicios;
      },
      error: err => console.error('Error al cargar datos:', err)
    });
  }

  totalSeries() {
    return this.itemsRutina!.reduce((total: number, item: ItemRutinaRes) => total + item!.series!.length, 0);
  }

  setItemToUpdate(id: number) {
    const itemToUpdate = this.itemsRutina.find(item => item.id === id)!;
    this.editingItem = JSON.parse(JSON.stringify(itemToUpdate));

  }

  appropriateExerciseType(typeOfExercise: EjercicioRes, type: string) {
// @ts-ignore
    const exerciseType: TipoDeEjercicioEnum = typeOfExercise.tipoDeEjercicio;
    switch (type) {
      case 'reps':
        return ['PESO_Y_REPETICIONES', 'PESO_CORPORAL', 'PESO_CORPORAL_CON_PESO_EXTRA', 'PESO_CORPORAL_ASISTIDO'].includes(exerciseType!);
      case 'distance' :
        return ['DISTANCIA_Y_PESO'].includes(exerciseType!);
      case 'time' :
        return ['DURACION', 'DISTANCIA_Y_DURACION'].includes(exerciseType!);
      case 'weigh' :
        return ['PESO_Y_REPETICIONES', 'PESO_CORPORAL_CON_PESO_EXTRA'].includes(exerciseType!);
      default:
        return false;
    }
  }

  removeItem(id: number) {
    this._itemRutinaService.eliminarItem(id, this.routineId).pipe(
      concatMap(() => {
        return this._itemRutinaService.getItems(this.routineId)
      })
    ).subscribe({
      next: (updatedItems) => this.itemsRutina = updatedItems
    })
  }

  addNewItem() {
    this.editingItem = undefined;
  }

  saveNewItem(savedItemEvent: SavedItemEvent) {
    if (savedItemEvent.id) {
      this._itemRutinaService.modificarItem(this.routineId, savedItemEvent.id, savedItemEvent.item).subscribe({
        next: item => {
          // Actualiza el item en el array itemsRutina
          const index = this.itemsRutina.findIndex(item => item.id === savedItemEvent.id);
          if (index !== -1)
            this.itemsRutina[index] = {...item} // Reemplaza el item modificado
        }
      })
    } else {
      this._itemRutinaService.crearItem(this.routineId, savedItemEvent.item).subscribe({
        next: (newItem) => {
          this.itemsRutina.push(newItem);
        },
        error: err => console.error('Error al guardar el item:', err)
      });
    }
  }


}
