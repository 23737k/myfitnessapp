package com.myfitnessapp.model.series;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_serie")
public abstract class Serie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  public abstract Serie clonar();
}
