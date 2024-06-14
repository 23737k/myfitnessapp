package com.myfitnessapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
  @NotEmpty(message = "El nombre no debe estar vacío")
  @NotNull(message = "El nombre no debe estar vacío")
  private String nombre;
  @NotEmpty(message = "La descripción no debe estar vacía")
  @NotNull(message = "La descripción no debe estar vacía")
  private String descripcion;
  private List<ItemRutinaRequestDto> items;

}
