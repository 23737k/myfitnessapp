package com.myfitnessapp.model.ejercicio;

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
  @Enumerated(EnumType.STRING)
  private GrupoMuscular grupoMuscularPrimario;
  @Enumerated(EnumType.STRING)
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
