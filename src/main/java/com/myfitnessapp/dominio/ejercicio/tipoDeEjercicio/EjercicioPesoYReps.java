package com.myfitnessapp.dominio.ejercicio.tipoDeEjercicio;

import com.myfitnessapp.dominio.series.Serie;
import com.myfitnessapp.dominio.series.SeriePesoYReps;

public class EjercicioPesoYReps implements TipoDeEjercicio {
  @Override
  public Serie crearSerie() {
    return new SeriePesoYReps();
  }

  @Override
  public boolean serieValida(Serie serie) {
    return serie.getClass().equals(SeriePesoYReps.class);
  }
}
