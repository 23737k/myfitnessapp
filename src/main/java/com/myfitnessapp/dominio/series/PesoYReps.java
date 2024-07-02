package com.myfitnessapp.dominio.series;

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
@DiscriminatorValue("peso_reps")
@NoArgsConstructor
@AllArgsConstructor
public class PesoYReps extends Serie {
  @Positive(message = "El campo reps debe ser un numero positivo")
  @NotNull(message = "El campo reps no debe estar vacío")
  private Integer reps;
  @Positive(message = "El campo pesoEnKg debe ser un numero positivo")
  @NotNull(message = "El campo pesoEnKg no debe estar vacío")
  private Double pesoEnKg;

  @Override
  public Serie clonar() {
    return new PesoYReps(this.reps,this.pesoEnKg);
  }
}
