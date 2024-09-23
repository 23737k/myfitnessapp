package com.myfitnessapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RutinaRes {
  private Integer id;
  private String nombre;
  private String descripcion;
}