package com.myfitnessapp.dto.response;

import com.myfitnessapp.model.ejercicio.TipoDeEjercicio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EjercicioRes {
  private Integer id;
  private String nombre;
  private Integer grupoMuscularPrimario;
  private Integer grupoMuscularSecundario;
  private TipoDeEjercicio tipoDeEjercicio;
}
