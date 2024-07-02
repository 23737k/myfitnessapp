package com.myfitnessapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenoRequestDto {
    private Integer rutinaId;
    private LocalDateTime inicio;
    private Integer duracionEnMinutos;
    private List<ItemRutinaRequestDto> items;
}
