package com.myfitnessapp.dominio.rutina;

import com.myfitnessapp.dominio.ejercicio.Ejercicio;
import com.myfitnessapp.dominio.series.Serie;
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
  @OneToMany
  @JoinColumn(name = "item_rutina_id")
  private List<Serie> series;

  public ItemRutina(Ejercicio ejercicio, int descansoEnSeg, String nota, List<Serie> series) {
    this.ejercicio = ejercicio;
    this.descansoEnSeg = descansoEnSeg;
    this.nota = nota;
    this.series = series;
  }

}
