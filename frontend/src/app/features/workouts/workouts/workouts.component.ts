import {Component, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";
import {ItemModalComponent} from "../../../shared/item-modal/item-modal.component";
import {ItemRutinaControllerService, ItemRutinaRes} from "../../../core/services/api-client";

@Component({
  selector: 'app-workouts',
  standalone: true,
  imports: [
    NgIf,
    ItemModalComponent
  ],
  templateUrl: './workouts.component.html',
  styleUrl: './workouts.component.css'
})
export class WorkoutsComponent implements OnInit{

  item?:ItemRutinaRes;

  constructor(private _itemService:ItemRutinaControllerService) {}

  ngOnInit(): void {
    this._itemService.getItems(1).subscribe({
      next: item => {
        this.item = item.pop();
      }
    })
  }

}
