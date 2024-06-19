package com.myfitnessapp.dto.request;

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
