package com.myfitnessapp.dominio.ejercicio;

import com.myfitnessapp.dominio.series.Serie;
import com.myfitnessapp.dto.request.SerieRequestDto;
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
  @Enumerated(EnumType.STRING)
  private TipoDeEjercicio tipoDeEjercicio;

  public Ejercicio(String nombre, GrupoMuscular grupoMuscularPrimario, GrupoMuscular grupoMuscularSecundario, TipoDeEjercicio tipoDeEjercicio) {
    this.nombre = nombre;
    this.grupoMuscularPrimario = grupoMuscularPrimario;
    this.grupoMuscularSecundario = grupoMuscularSecundario;
    this.tipoDeEjercicio = tipoDeEjercicio;
  }

}
