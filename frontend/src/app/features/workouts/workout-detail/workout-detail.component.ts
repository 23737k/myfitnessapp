import { CommonModule } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import { EntrenoRes } from '../../../core/services/api-client';
import { PieChartComponent } from "../../../shared/chart/pie-chart/pie-chart.component";

@Component({
  selector: 'app-workout-detail',
  standalone: true,
  imports: [CommonModule, PieChartComponent],
  templateUrl: './workout-detail.component.html',
  styleUrl: './workout-detail.component.css'
})
export class WorkoutDetailComponent {
  @Input() workout? : EntrenoRes = {};

  activeModal = inject(NgbActiveModal);
}
