package com.myfitnessapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
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
  @NotEmpty
  private String nombre;
  @NotEmpty
  private String descripcion;
  private List<ItemRutinaRequestDto> items;

}
