package com.myfitnessapp.dominio;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRutina {
  private Ejercicio ejercicio;
  private int descansoEnSeg;
  private String nota;
  private List<Serie> series;

  public ItemRutina(Ejercicio ejercicio, int descansoEnSeg, String nota) {
    this.ejercicio = ejercicio;
    this.descansoEnSeg = descansoEnSeg;
    this.nota = nota;
    this.series = new ArrayList<>();
  }

  public void addSerie(Serie serie) {
    if(ejercicio.serieValida(serie))
      this.series.add(serie);
  }

}
