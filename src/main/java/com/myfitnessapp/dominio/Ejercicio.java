package com.myfitnessapp.dominio;

import lombok.Data;

@Data
public class Ejercicio {
  private String nombre;
  private GrupoMuscular grupoMuscularPrimario;
  private GrupoMuscular grupoMuscularSecundario;
  private TipoDeEjercicio tipoDeEjercicio;

  public Serie crearSerie(){
    return this.getTipoDeEjercicio().crearSerie();
  }

  public boolean serieValida(Serie serie){
    return tipoDeEjercicio.crearSerie().getClass().equals(serie.getClass());
  }

}
