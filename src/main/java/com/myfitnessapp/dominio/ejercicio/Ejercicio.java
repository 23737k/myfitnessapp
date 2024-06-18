package com.myfitnessapp.dominio.ejercicio;

import com.myfitnessapp.dominio.ejercicio.tipoDeEjercicio.TipoDeEjercicio;
import com.myfitnessapp.dominio.series.Serie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Ejercicio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String nombre;
  @ManyToOne
  @JoinColumn(name = "grupo_muscular_primario_id")
  private GrupoMuscular grupoMuscularPrimario;
  @ManyToOne
  @JoinColumn(name = "grupo_muscular_secundario_id")
  private GrupoMuscular grupoMuscularSecundario;
//  @ManyToOne
//  @JoinColumn(name = "tipo_de_ejercicio_id")
  @Transient
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
