package com.myfitnessapp.dominio.rutina;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Rutina {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String nombre;
  private String descripcion;
  @Transient
  private List<ItemRutina> items;

  public Rutina(String nombre, String descripcion, List<ItemRutina> items) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.items = items;
  }
}
