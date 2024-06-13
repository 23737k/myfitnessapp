package com.myfitnessapp.dominio.series;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeriePesoYReps extends Serie {
  private int reps;
  private double pesoEnKg;

  public SeriePesoYReps(int reps, double pesoEnKg) {
    this.reps = reps;
    this.pesoEnKg = pesoEnKg;
  }
  public SeriePesoYReps() {
  }
}
