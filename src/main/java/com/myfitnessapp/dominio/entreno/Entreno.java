package com.myfitnessapp.dominio.entreno;

import static com.myfitnessapp.dominio.ejercicio.TipoDeEjercicio.PESO_Y_REPETICIONES;
import com.myfitnessapp.dominio.rutina.ItemRutina;
import com.myfitnessapp.dominio.rutina.Rutina;
import com.myfitnessapp.dominio.series.PesoYReps;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Entreno {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer duracionEnMinutos;
  private Integer volumen;
  private LocalDateTime inicio;
  private Integer nroDeSeries;
  @ManyToOne(cascade = CascadeType.ALL)
  private Rutina rutina;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "entreno_id")
  @OrderColumn(name="item_order")
  private List<ItemRutina> items;

  protected Entreno(){}

  public Entreno (Rutina rutina, List<ItemRutina> items, Integer duracionEnMinutos, LocalDateTime inicio){
    this.rutina = rutina;
    this.duracionEnMinutos = duracionEnMinutos;
    this.inicio = inicio;
    this.items = items == null? rutina.getItems(): items;
    this.nroDeSeries = calcularNroSeries();
    this.volumen = calcularVolumen();
  }

  public int calcularVolumen(){
    List<ItemRutina> items = this.items.stream().filter(i->i.getEjercicio().getTipoDeEjercicio().equals(PESO_Y_REPETICIONES)).toList();
    List<PesoYReps> series = items.stream().flatMap(i -> i.getSeries().stream()).map(s -> (PesoYReps) s).toList();
    return series.stream().mapToInt(s-> s.getPesoEnKg().intValue()).sum();
  }

  public int calcularNroSeries(){
    return this.items.stream().mapToInt(i -> i.getSeries().size()).sum();
  }


}
