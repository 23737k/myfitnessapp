package com.myfitnessapp.dto.response;

import com.myfitnessapp.model.ejercicio.GrupoMuscular;
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
  private GrupoMuscular grupoMuscularPrimario;
  private GrupoMuscular grupoMuscularSecundario;
  private TipoDeEjercicio tipoDeEjercicio;
}
