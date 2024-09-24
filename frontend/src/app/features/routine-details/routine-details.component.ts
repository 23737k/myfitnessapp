import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {
  EjercicioControllerService,
  EjercicioRes,
  ItemRutinaControllerService,
  ItemRutinaRes,
  RutinaControllerService,
  RutinaRes,
  SerieRes
} from "../../core/services/api-client";
import {NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
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
  itemsRutina: ItemRutinaRes[]=[];
  routineId!: number;
  editingItem!: ItemRutinaRes;
  exercises!: EjercicioRes[];

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _router: Router,
    private _rutinaService: RutinaControllerService,
    private _itemRutinaService: ItemRutinaControllerService,
    private _ejercicioService : EjercicioControllerService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.routineId = this._activatedRoute.snapshot.params['id'];
    this._itemRutinaService.getItems(this.routineId).subscribe({
      next: items => this.itemsRutina = items
    });
    this._rutinaService.getRutinaById(this.routineId).subscribe({
      next : rutina => this.rutina = rutina});

    this._ejercicioService.listarEjercicios().subscribe({
      next: ejercicios => this.exercises = ejercicios
    })

    this.editingItem = new ItemRutina();

  }

  totalSeries(){
    return this.itemsRutina.reduce((total: number, item: ItemRutinaRes) => total + item!.series!.length, 0);

  }

  setItemToUpdate(id: number) {
    this.editingItem = this.itemsRutina.find(item => item.id === id)!;
    if (this.editingItem.ejercicio) {
      this.editingItem.ejercicio = this.exercises.find(ej => ej.id === this.editingItem.ejercicio!.id!); // Asigna el objeto completo
    }
  }

  appopiateExerciseType(exercise: EjercicioRes, type : string) {
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
export class ItemRutina implements ItemRutinaRes{
  id?: number;
  ejercicio?: EjercicioRes;
  descansoEnSeg?: number;
  nota?: string;
  series?: Array<SerieRes>;
}
