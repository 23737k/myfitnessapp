package com.myfitnessapp.dto.request;

import com.myfitnessapp.validation.Crear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
  @NotNull(message = "El campo ejercicioId no debe estar vacío", groups = Crear.class)
  @Schema(example = "3")
  private Integer ejercicioId;
  @Positive(message = "El campo descansoEnSeg debe ser un numero positivo")
  @NotNull(message = "El campo descansoEnSeg no debe estar vacío", groups = Crear.class)
  @Schema(example = "120")
  private Integer descansoEnSeg;
  @NotNull(message = "El campo nota no debe esta vacío", groups = Crear.class)
  @Schema(example = "descenso controlado")
  private String nota;
  @NotEmpty(message = "El campo series no debe esta vacío", groups = Crear.class)
  @NotNull(message = "El campo series no debe esta vacío", groups = Crear.class)
  @Size(min = 1, message = "Debe haber al menos una serie", groups = Crear.class)
  private List<SerieRequestDto> series;

}
