import { Component, Input, OnInit } from '@angular/core';
import { ChartOptions, ChartType, TooltipItem } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [
    BaseChartDirective
  ],
  templateUrl: './chart.component.html',
  styleUrl: './chart.component.css'
})
export class ChartComponent implements OnInit {
  @Input() data!: number[];
  @Input() labels!: string[];

  chartType: ChartType = 'pie';
  datasets!: any[];
  options!: ChartOptions;

  ngOnInit() {
    this.datasets = [{
      data: this.data,
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)'
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)'
      ],
      borderWidth: 1
    }];

    const total = this.data.reduce((acc, value) => acc + value, 0);

    this.options = {
      plugins: {
        tooltip: {
          callbacks: {
            label: (tooltipItem: TooltipItem<'pie'>) => {
              const label = this.labels[tooltipItem.dataIndex];
              const value = this.data[tooltipItem.dataIndex];
              const percentage = ((value / total) * 100).toFixed(2); // Calcular el porcentaje
              return `${label}: ${percentage}%`;
            }
          }
        }
      }
    };
  }
}
