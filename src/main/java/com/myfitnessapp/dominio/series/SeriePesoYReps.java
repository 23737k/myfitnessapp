package com.myfitnessapp.dominio.series;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("peso_reps")
@NoArgsConstructor
public class SeriePesoYReps extends Serie {
  private int reps;
  private double pesoEnKg;

  public SeriePesoYReps(int reps, double pesoEnKg) {
    this.reps = reps;
    this.pesoEnKg = pesoEnKg;
  }
}
