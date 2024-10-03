package com.myfitnessapp.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemRutinaRes {
  private Integer id;
  private EjercicioRes ejercicio;
  private int descansoEnSeg;
  private String nota;
  private List<SerieRes> series;
}
