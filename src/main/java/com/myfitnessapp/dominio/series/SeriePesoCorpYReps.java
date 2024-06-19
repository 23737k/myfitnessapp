package com.myfitnessapp.dominio.series;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("pesoCorp_reps")
@NoArgsConstructor
public class SeriePesoCorpYReps extends Serie{
  private int reps;

  public SeriePesoCorpYReps(int reps){
    this.reps = reps;
  }

}
