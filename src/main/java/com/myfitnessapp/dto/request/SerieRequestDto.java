package com.myfitnessapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerieRequestDto {
  private Integer reps;
  private Double pesoEnKg;
  private Integer tiempoEnSeg;
  private Double distancia;
}
