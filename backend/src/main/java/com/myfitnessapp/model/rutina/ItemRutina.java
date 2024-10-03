package com.myfitnessapp.model.rutina;

import com.myfitnessapp.model.ejercicio.Ejercicio;
import com.myfitnessapp.model.series.Serie;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ItemRutina {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  private Ejercicio ejercicio;
  private int descansoEnSeg;
  private String nota;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "item_rutina_id")
  private List<Serie> series;

  public ItemRutina(Ejercicio ejercicio, int descansoEnSeg, String nota, List<Serie> series) {
    this.ejercicio = ejercicio;
    this.descansoEnSeg = descansoEnSeg;
    this.nota = nota;
    this.series = series;
  }
  public void setSeries(List<Serie> seriesList){
    if (this.series != null) {
      this.series.clear();
      if (seriesList != null) {
        this.series.addAll(seriesList);
      }
    } else {
      this.series = seriesList;
    }
  }

  public ItemRutina clonar(){
    List<Serie> series = this.series.stream().map(Serie::clonar).toList();
    return new ItemRutina(this.getEjercicio(),this.getDescansoEnSeg(),this.getNota(),series);
  }

}
