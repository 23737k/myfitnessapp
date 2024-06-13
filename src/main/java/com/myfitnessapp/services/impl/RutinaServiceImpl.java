package com.myfitnessapp.services.impl;

import com.myfitnessapp.dominio.rutina.Rutina;
import com.myfitnessapp.dto.request.RutinaRequestDto;
import com.myfitnessapp.dto.response.RutinaResponseDto;
import com.myfitnessapp.repositories.RutinaRepo;
import com.myfitnessapp.services.RutinaService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RutinaServiceImpl implements RutinaService {
  private final RutinaRepo rutinaRepo;

  public RutinaServiceImpl(RutinaRepo rutinaRepo) {
    this.rutinaRepo = rutinaRepo;
  }

  // Mapping methods
  @Override
  public Rutina toRutina(RutinaRequestDto rDto) {
    return new Rutina(rDto.getNombre(), rDto.getDescripcion(),null);
  }
  @Override
  public RutinaResponseDto toRutinaResponseDto(Rutina rutina) {
    return new RutinaResponseDto(rutina.getId(), rutina.getNombre(), rutina.getDescripcion());
  }
  //

  @Override
  public RutinaResponseDto saveRutina(RutinaRequestDto rutinaRequestDto) {
    Rutina rutina = rutinaRepo.save(toRutina(rutinaRequestDto));
    return new RutinaResponseDto(rutina.getId(), rutina.getNombre(), rutina.getDescripcion());
  }

  @Override
  public RutinaResponseDto findRutinaById(Integer id) {
    Rutina rutina = rutinaRepo.findById(id).orElse(null);
    return rutina != null ? toRutinaResponseDto(rutina) : null ;
  }

  @Override
  public RutinaResponseDto deleteRutinaById(Integer id) {
    Rutina rutina = rutinaRepo.findById(id).orElse(null);
    rutinaRepo.deleteById(id);
    return rutina != null ? toRutinaResponseDto(rutina) : null;
  }


  @Override
  public List<RutinaResponseDto> getRutinas() {
    return rutinaRepo.findAll().stream().map(this::toRutinaResponseDto).toList();
  }

}
