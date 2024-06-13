package com.myfitnessapp.dominio;

import java.util.List;
import lombok.Data;

@Data
public class Rutina {
  private String nombre;
  private String descripcion;
  private List<ItemRutina> items;

}
