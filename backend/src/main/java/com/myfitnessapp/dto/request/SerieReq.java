package com.myfitnessapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SerieReq {
  private Integer reps;
  private Double pesoEnKg;
  private Integer tiempoEnSeg;
  private Double distancia;
}
