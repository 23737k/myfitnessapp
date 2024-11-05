import { CommonModule } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import { EntrenoRes } from '../../../core/services/api-client';
import { ChartComponent } from "../../../shared/chart/chart.component";

@Component({
  selector: 'app-workout-detail',
  standalone: true,
  imports: [CommonModule, ChartComponent],
  templateUrl: './workout-detail.component.html',
  styleUrl: './workout-detail.component.css'
})
export class WorkoutDetailComponent {
  @Input() workout? : EntrenoRes = {};
  @Input() data: number[] = [];
  @Input() labels : string[] = [];

  activeModal = inject(NgbActiveModal);
}
