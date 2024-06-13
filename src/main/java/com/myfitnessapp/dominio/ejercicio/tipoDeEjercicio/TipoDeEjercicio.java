package com.myfitnessapp.dominio.ejercicio.tipoDeEjercicio;


import com.myfitnessapp.dominio.series.Serie;

public interface TipoDeEjercicio {
  Serie crearSerie();
  boolean serieValida(Serie serie);
}
