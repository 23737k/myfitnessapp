package com.myfitnessapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerieResponseDto {
    private Integer id;
    private Integer reps;
    private Double pesoEnKg;
    private Integer tiempoEnSeg;
    private Double distancia;
}
