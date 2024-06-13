package com.myfitnessapp.services;

import com.myfitnessapp.dominio.rutina.Rutina;
import com.myfitnessapp.dto.request.RutinaRequestDto;
import com.myfitnessapp.dto.response.RutinaResponseDto;
import java.util.List;

public interface RutinaService {

  RutinaResponseDto saveRutina(RutinaRequestDto rutina);
  Rutina toRutina(RutinaRequestDto rDto);
  List<RutinaResponseDto> getRutinas();
  RutinaResponseDto toRutinaResponseDto(Rutina rutina);
  RutinaResponseDto findRutinaById(Integer id);
  RutinaResponseDto deleteRutinaById(Integer id);
}
