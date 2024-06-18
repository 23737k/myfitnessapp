package com.myfitnessapp.dominio.series;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SerieTiempo extends Serie{
    private int tiempoEnSeg;
}
