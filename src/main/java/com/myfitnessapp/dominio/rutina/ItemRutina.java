package com.myfitnessapp.dominio.rutina;

import com.myfitnessapp.dominio.ejercicio.Ejercicio;
import com.myfitnessapp.dominio.series.Serie;
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

  public ItemRutina(Ejercicio ejercicio, int descansoEnSeg, String nota, List<Serie> series) {
    this.ejercicio = ejercicio;
    this.descansoEnSeg = descansoEnSeg;
    this.nota = nota;
    this.series = series.stream().filter(ejercicio::serieValida).toList();
  }


  public void agregarSerie() {
    this.series.add(ejercicio.crearSerie());
  }

}
