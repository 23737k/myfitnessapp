package com.myfitnessapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerieRequestDto {
  private int reps;
  private double pesoEnKg;
  private int tiempoEnSeg;
  private double distancia;
}
