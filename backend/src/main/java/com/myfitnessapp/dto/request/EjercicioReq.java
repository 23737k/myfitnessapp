package com.myfitnessapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EjercicioReq {
  @NotBlank(message = "El campo nombre no debe estar vacío.")
  private String nombre;
  @NotNull(message = "El campo grupo muscular primario no debe estar vacío.")
  private Integer grupoMuscularPrimario;
  private Integer grupoMuscularSecundario;
  @NotEmpty(message = "El campo tipo de ejercicio no debe estar vacío.")
  private String tipoDeEjercicio;
}
