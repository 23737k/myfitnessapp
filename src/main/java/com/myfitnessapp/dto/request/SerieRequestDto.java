package com.myfitnessapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerieRequestDto {
  private int reps;
  private double pesoEnKg;
  private double duracionEnSeg;
  private double distancia;
}
