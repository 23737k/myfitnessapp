package com.myfitnessapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RutinaRes {
  private Integer id;
  private String nombre;
  private String descripcion;
}