package com.myfitnessapp.model.series;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("peso_corporal_reps")
@NoArgsConstructor
@AllArgsConstructor
public class PesoCorpYReps extends Serie{
  @Positive(message = "El campo reps debe ser un numero positivo")
  @NotNull(message = "El campo reps no debe estar vacío")
  private Integer reps;


  @Override
  public Serie clonar() {
    return new PesoCorpYReps(this.getReps());
  }
}
