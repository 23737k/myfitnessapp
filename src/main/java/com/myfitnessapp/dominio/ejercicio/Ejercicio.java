package com.myfitnessapp.dominio.ejercicio;

import com.myfitnessapp.dominio.ejercicio.tipoDeEjercicio.TipoDeEjercicio;
import com.myfitnessapp.dominio.series.Serie;
import lombok.Data;

@Data
public class Ejercicio {
  private String nombre;
  private GrupoMuscular grupoMuscularPrimario;
  private GrupoMuscular grupoMuscularSecundario;
  private TipoDeEjercicio tipoDeEjercicio;

  public Ejercicio(String nombre, GrupoMuscular grupoMuscularPrimario, GrupoMuscular grupoMuscularSecundario, TipoDeEjercicio tipoDeEjercicio) {
    this.nombre = nombre;
    this.grupoMuscularPrimario = grupoMuscularPrimario;
    this.grupoMuscularSecundario = grupoMuscularSecundario;
    this.tipoDeEjercicio = tipoDeEjercicio;
  }

  public boolean serieValida(Serie serie){
    return tipoDeEjercicio.serieValida(serie);
  }

  public Serie crearSerie(){
    return tipoDeEjercicio.crearSerie();
  }

}
