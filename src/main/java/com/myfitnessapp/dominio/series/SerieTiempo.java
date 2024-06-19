package com.myfitnessapp.dominio.series;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("tiempo")
public class SerieTiempo extends Serie{
    @Positive(message = "El campo tiempoEnSeg debe ser un numero positivo")
    @NotNull(message = "El campo tiempoEnSeg no debe estar vacío")
    private Integer tiempoEnSeg;
}
