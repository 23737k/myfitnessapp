package com.myfitnessapp.dominio.series;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeriePesoCorpYReps extends Serie{
  private int reps;

  public SeriePesoCorpYReps(int reps){
    this.reps = reps;
  }

}
