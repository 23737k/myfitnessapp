package com.myfitnessapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ItemRutinaRes {
  private Integer id;
  private String ejercicio;
  private int descansoEnSeg;
  private String nota;
  private List<SerieRes> series;
}
