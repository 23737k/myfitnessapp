package com.myfitnessapp.dominio.series;

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
@Entity
@DiscriminatorValue("distancia_peso")
@NoArgsConstructor
@AllArgsConstructor
public class DistanciaYPeso extends Serie{
    @Positive(message = "El campo distancia debe ser un numero positivo")
    @NotNull(message = "El campo distancia no debe estar vacío")
    private Double distancia;
    @Positive(message = "El campo distancia debe ser un numero positivo")
    @NotNull(message = "El campo distancia no debe estar vacío")
    private Double pesoEnKg;
}
