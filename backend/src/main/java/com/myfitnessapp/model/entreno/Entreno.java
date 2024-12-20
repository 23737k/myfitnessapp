package com.myfitnessapp.model.entreno;

import static com.myfitnessapp.model.ejercicio.TipoDeEjercicio.PESO_Y_REPETICIONES;

import com.myfitnessapp.model.rutina.ItemRutina;
import com.myfitnessapp.model.rutina.Rutina;
import com.myfitnessapp.model.series.PesoYReps;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Entreno {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Long duracionEnSeg;
  private Integer volumen;
  private LocalDateTime fecha;
  private Integer nroDeSeries;
  private String titulo;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "entreno_id")
  @OrderColumn(name="item_order")
  private List<ItemRutina> items;

  protected Entreno(){}

  public Entreno (Rutina rutina, List<ItemRutina> items, Long duracionEnSeg, LocalDateTime fecha){
    this.duracionEnSeg = duracionEnSeg;
    this.fecha = fecha;
    this.items = items == null? rutina.getItems().stream().map(ItemRutina::clonar).toList():items;
    this.titulo = rutina.getNombre();
    this.nroDeSeries = calcularNroSeries();
    this.volumen = calcularVolumen();
  }

  public int calcularVolumen(){
    List<ItemRutina> items = this.items.stream().filter(i->i.getEjercicio().getTipoDeEjercicio().equals(PESO_Y_REPETICIONES)).toList();
    List<PesoYReps> series = items.stream().flatMap(i -> i.getSeries().stream()).map(s -> (PesoYReps) s).toList();
    return (int) series.stream().mapToDouble(s -> s.getPesoEnKg() * s.getReps()).sum();
  }

  public int calcularNroSeries(){
    return this.items.stream().mapToInt(i -> i.getSeries().size()).sum();
  }


}
