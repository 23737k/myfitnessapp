package com.myfitnessapp.model.rutina;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "rutina_id")
  @OrderColumn(name="item_order")
  private List<ItemRutina> items;

  public Rutina(String nombre, String descripcion, List<ItemRutina> items) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.items = items;
  }

  public void setItems(List<ItemRutina> itemRutinaList){
    if (this.items != null) {
      this.items.clear();
      if (itemRutinaList != null) {
        this.items.addAll(itemRutinaList);
      }
    } else {
      this.items = itemRutinaList;
    }
  }
}
