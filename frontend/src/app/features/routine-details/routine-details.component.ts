import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {
  EjercicioControllerService,
  EjercicioRes,
  ItemRutinaControllerService, ItemRutinaReq,
  ItemRutinaRes,
  RutinaControllerService,
  RutinaRes, SerieReq, SerieRes
} from "../../core/services/api-client";
import {NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import 'bootstrap';
import {concatMap, forkJoin, switchMap} from "rxjs";
import TipoDeEjercicioEnum = EjercicioRes.TipoDeEjercicioEnum;

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
    const itemToUpdate = this.itemsRutina.find(item => item.id === id)!;
    this.editingItem = JSON.parse(JSON.stringify(itemToUpdate));

// Busca el ejercicio correspondiente en la lista de ejercicios
    this.editingItem.ejercicio = this.exercises.find(exercise => exercise.id === this.editingItem.ejercicio!.id!);

    const modalElement = document.getElementById('editItemModal');
// @ts-ignore
    const modal = new bootstrap.Modal(modalElement!);
    modal.show();
  }

  appropriateExerciseType(typeOfExercise: EjercicioRes, type: string) {
// @ts-ignore
    const exerciseType: TipoDeEjercicioEnum = typeOfExercise.tipoDeEjercicio;
    switch (type) {
      case 'reps': return ['PESO_Y_REPETICIONES','PESO_CORPORAL','PESO_CORPORAL_CON_PESO_EXTRA','PESO_CORPORAL_ASISTIDO'].includes(exerciseType!);
      case 'distance' : return ['DISTANCIA_Y_PESO'].includes(exerciseType!);
      case 'time' : return ['DURACION','DISTANCIA_Y_DURACION'].includes(exerciseType!);
      case 'weigh' : return ['PESO_Y_REPETICIONES', 'PESO_CORPORAL_CON_PESO_EXTRA'].includes(exerciseType!);
      default: return false;
    }
  }

  editItem() {
    const itemReq: ItemRutinaReq = ({
      ejercicioId: this.editingItem.ejercicio?.id!,
      descansoEnSeg: this.editingItem.descansoEnSeg!,
      nota: this.editingItem.nota!,
      series: this.editingItem.series?.map(s => ({
        reps: s.reps,
        pesoEnKg: s.pesoEnKg,
        tiempoEnSeg: s.tiempoEnSeg,
        distancia: s.distancia
      }))!
    });

    this._itemRutinaService.modificarItem(this.routineId, this.editingItem.id!, itemReq).subscribe({
      next: (updatedItem) => {
// Actualiza el item en el array itemsRutina
        const index = this.itemsRutina.findIndex(item => item.id === this.editingItem.id);
        if (index !== -1) {
          this.itemsRutina[index] = {...updatedItem}; // Reemplaza el item modificado
        }

// Cierra el modal (si lo usas para editar)
        const modalElement = document.getElementById('editItemModal');
// @ts-ignore
        const modal = bootstrap.Modal.getInstance(modalElement)!;
        modal.hide();

      },
      error: err => console.error('Error al modificar el item:', err)
    });
  }

  updateExercise(exercise: EjercicioRes) {
    this.editingItem.ejercicio = exercise;
  }

  addNewSet(){
    this.editingItem.series?.push({});
  }
  removeSet(index:number){
    console.log(index)
    this.editingItem.series!.splice(index, 1);
  }

  removeItem(id:number){
    this._itemRutinaService.eliminarItem(id, this.routineId).pipe(
      concatMap(()=> {return this._itemRutinaService.getItems(this.routineId)})
    ).subscribe({
      next: (updatedItems) => this.itemsRutina = updatedItems
    })
  }

  addNewItem(){
    const newItem = {
      ejercicio: {},
      descansoEnSeg: 0,
      nota: "",
      series: []
    };
    this.editingItem = JSON.parse(JSON.stringify(newItem));
    const modalElement = document.getElementById('newItemModal');
// @ts-ignore
    const modal = new bootstrap.Modal(modalElement!);
    modal.show();
  }

  saveNewItem() {
    const itemReq: ItemRutinaReq = ({
      ejercicioId: this.editingItem.ejercicio?.id!,
      descansoEnSeg: this.editingItem.descansoEnSeg!,
      nota: this.editingItem.nota!,
      series: this.editingItem.series?.map(s => ({
        reps: s.reps,
        pesoEnKg: s.pesoEnKg,
        tiempoEnSeg: s.tiempoEnSeg,
        distancia: s.distancia
      }))!
    });

    if(itemReq.series.length ===0){
      alert("Debe haber al menos una serie");
      return;
    }

    console.log(itemReq);

    this._itemRutinaService.crearItem(this.routineId, itemReq).subscribe({
      next: (newItem) => {
// Actualiza el item en el array itemsRutina
        const index = this.itemsRutina.findIndex(item => item.id === this.editingItem.id);
        if (index !== -1) {
          this.itemsRutina[index] = {...newItem}; // Reemplaza el item modificado
        }

// Cierra el modal (si lo usas para editar)
        const modalElement = document.getElementById('newItemModal');
// @ts-ignore
        const modal = bootstrap.Modal.getInstance(modalElement)!;
        modal.hide();

        this.itemsRutina.push(newItem as ItemRutinaRes);

      },
      error: err => console.error('Error al guardar el item:', err)
    });
  }


}
