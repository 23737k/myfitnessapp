package com.myfitnessapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EjercicioRequestDto {
  private String nombre;
  private Integer grupoMuscularPrimario;
  private Integer grupoMuscularSecundario;
  private String tipoDeEjercicio;
}
