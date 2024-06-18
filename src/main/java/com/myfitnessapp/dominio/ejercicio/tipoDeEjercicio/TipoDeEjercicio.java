package com.myfitnessapp.dominio.ejercicio.tipoDeEjercicio;


import com.myfitnessapp.dominio.series.Serie;
import jakarta.persistence.*;
import lombok.Data;

//@Entity
//@Table
//@Data
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "tipo_de_ejercicio")
public abstract class TipoDeEjercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public abstract Serie crearSerie();
    public abstract boolean serieValida(Serie serie);
}
