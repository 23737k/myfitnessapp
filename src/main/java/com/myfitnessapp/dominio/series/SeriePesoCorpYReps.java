package com.myfitnessapp.dominio.series;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("pesoCorp_reps")
@NoArgsConstructor
@AllArgsConstructor
public class SeriePesoCorpYReps extends Serie{
  @Positive(message = "El campo reps debe ser un numero positivo")
  @NotNull(message = "El campo reps no debe estar vacío")
  private Integer reps;


}
