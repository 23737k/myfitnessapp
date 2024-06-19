package com.myfitnessapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EjercicioRequestDto {
  @NotEmpty(message = "El campo nombre no debe estar vacío.")
  @NotNull(message = "El campo nombre no debe estar vacío.")
  private String nombre;
  @NotEmpty(message = "El campo grupo muscular primario no debe estar vacío.")
  @NotNull(message = "El campo grupo muscular primario no debe estar vacío.")
  private Integer grupoMuscularPrimario;
  @NotEmpty(message = "El campo grupo muscular secundario no debe estar vacío.")
  @NotNull(message = "El campo grupo muscular secundario no debe estar vacío.")
  private Integer grupoMuscularSecundario;
  @NotEmpty(message = "El campo tipo de ejercicio no debe estar vacío.")
  @NotNull(message = "El campo tipo de ejercicio no debe estar vacío.")
  private String tipoDeEjercicio;
}
