package com.myfitnessapp.model.series;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("duracion")
public class Duracion extends Serie{
    @Positive(message = "El campo tiempoEnSeg debe ser un numero positivo")
    @NotNull(message = "El campo tiempoEnSeg no debe estar vacío")
    private Integer tiempoEnSeg;

    @Override
    public Serie clonar() {
        return new Duracion(this.tiempoEnSeg);
    }
}
