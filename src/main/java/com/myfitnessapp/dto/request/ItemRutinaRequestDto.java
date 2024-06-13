package com.myfitnessapp.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemRutinaRequestDto {
  private Integer ejercicioId;
  private double descansoEnSeg;
  private String nota;
  private List<SerieRequestDto> series;
}
