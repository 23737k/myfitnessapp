package com.myfitnessapp.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemRutinaRequestDto {
  @Positive(message = "El campo ejercicioId debe ser un numero positivo")
  @NotNull(message = "El campo ejercicioId no debe estar vacío")
  private Integer ejercicioId;
  @Positive(message = "El campo descansoEnSeg debe ser un numero positivo")
  @NotNull(message = "El campo descansoEnSeg no debe estar vacío")
  private Integer descansoEnSeg;
  @NotEmpty(message = "El campo nota no debe esta vacío")
  @NotNull(message = "El campo nota no debe esta vacío")
  private String nota;
  @NotEmpty(message = "El campo series no debe esta vacío")
  @NotNull(message = "El campo series no debe esta vacío")
  @Size(min = 1, message = "Debe haber al menos una serie")
  private List<SerieRequestDto> series;
  @PositiveOrZero(message = "El indice debe ser positivo o cero")
  private Integer indice;
}
