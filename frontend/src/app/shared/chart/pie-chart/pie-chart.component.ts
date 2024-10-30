import {Component, Input, OnInit} from '@angular/core';
import { ChartOptions, ChartType} from "chart.js";
import {BaseChartDirective} from "ng2-charts";

@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [
    BaseChartDirective
  ],
  templateUrl: './pie-chart.component.html',
  styleUrl: './pie-chart.component.css'
})
export class PieChartComponent implements OnInit{
  @Input() data!: number[];
  @Input() labels!: string[];

  chartType: ChartType = 'pie';
  datasets!: any[];
  options!: ChartOptions;

  ngOnInit() {
    console.log(this.data)
    console.log(this.labels)
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
    this.options = {
      plugins: {
        tooltip: {

        }
      }
    };
  }
}
