package com.myfitnessapp.dominio.ejercicio.tipoDeEjercicio;

import com.myfitnessapp.dominio.series.Serie;
import com.myfitnessapp.dominio.series.SerieTiempo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//@Entity
//@Table
//@DiscriminatorValue("tiempo")
public class EjercicioTiempo extends TipoDeEjercicio{

    @Override
    public Serie crearSerie() {
        return new SerieTiempo();
    }

    @Override
    public boolean serieValida(Serie serie) {
        return false;
    }
}
