package com.myfitnessapp.model.rutina;

import com.myfitnessapp.model.entreno.Entreno;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
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
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "rutina_id")
  @OrderColumn(name="item_order")
  private List<ItemRutina> items;
  @OneToMany
  @JoinColumn(name = "rutina_id")
  List<Entreno> entrenos;

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
