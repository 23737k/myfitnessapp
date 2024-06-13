package com.myfitnessapp.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RutinaRequestDto {
  private String nombre;
  private String descripcion;
  private List<ItemRutinaRequestDto> items;

}
