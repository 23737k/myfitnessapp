import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {
  ItemRutinaControllerService,
  ItemRutinaRes,
  RutinaControllerService,
  RutinaRes
} from "../../core/services/api-client";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-routine-details',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './routine-details.component.html',
  styleUrl: './routine-details.component.css'
})
export class RoutineDetailsComponent implements OnInit {

  rutina!: RutinaRes;
  itemsRutina!: ItemRutinaRes[];
  routineId!: number;

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _router: Router,
    private _rutinaService: RutinaControllerService,
    private _itemRutinaService: ItemRutinaControllerService
  ) {
  }

  ngOnInit(): void {
    this.routineId = this._activatedRoute.snapshot.params['id'];
    this._rutinaService.getRutinaById(this.routineId).subscribe({
      next : rutina => this.rutina = rutina})
    this._itemRutinaService.getItems(this.routineId).subscribe({
      next: items => this.itemsRutina = items
    })
  }


}
