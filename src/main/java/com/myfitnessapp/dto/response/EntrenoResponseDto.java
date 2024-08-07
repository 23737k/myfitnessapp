package com.myfitnessapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenoResponseDto{
    private Integer id;
    private Integer rutinaId;
    private String rutinaNombre;
    private LocalDateTime inicio;
    private Integer duracionEnMinutos;
    private Integer volumenEnKg;
    private Integer nroDeSeries;
    private List<ItemRutinaResponseDto> items;
}
