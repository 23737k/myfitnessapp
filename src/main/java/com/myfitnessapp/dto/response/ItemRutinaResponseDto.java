package com.myfitnessapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemRutinaResponseDto {
  private Integer id;
  private String ejercicio;
  private int descansoEnSeg;
  private String nota;
}
