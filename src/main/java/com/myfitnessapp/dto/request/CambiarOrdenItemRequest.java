package com.myfitnessapp.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CambiarOrdenItemRequest {
  @NotNull(message = "El campo indice no debe estar vacío")
  @PositiveOrZero(message = "El indice debe ser mayor o igual a cero")
  private Integer indice;
  @NotNull(message = "El campo idItem no debe estar vacío")
  @PositiveOrZero(message = "El idItem debe ser mayor o igual a cero")
  private Integer itemId;

}
