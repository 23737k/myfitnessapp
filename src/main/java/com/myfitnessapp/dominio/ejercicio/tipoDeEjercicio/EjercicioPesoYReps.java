package com.myfitnessapp.dominio.ejercicio.tipoDeEjercicio;

import com.myfitnessapp.dominio.series.Serie;
import com.myfitnessapp.dominio.series.SeriePesoYReps;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

//@Entity
//@Table
//@DiscriminatorValue("peso_reps")
public class EjercicioPesoYReps extends TipoDeEjercicio {
  @Override
  public Serie crearSerie() {
    return new SeriePesoYReps();
  }

  @Override
  public boolean serieValida(Serie serie) {
    return serie.getClass().equals(SeriePesoYReps.class);
  }
}
