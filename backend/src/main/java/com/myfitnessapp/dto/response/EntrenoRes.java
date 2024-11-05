package com.myfitnessapp.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenoRes {
    private Integer id;
    private String titulo;
    private LocalDateTime fecha;
    private Long duracionEnSeg;
    private Integer volumenEnKg;
    private Integer nroDeSeries;
    private List<ItemRutinaRes> items;
}
