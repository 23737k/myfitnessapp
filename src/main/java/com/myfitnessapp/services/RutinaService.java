package com.myfitnessapp.services;

import com.myfitnessapp.dominio.rutina.ItemRutina;
import com.myfitnessapp.dominio.rutina.Rutina;
import com.myfitnessapp.dto.request.ItemRutinaRequestDto;
import com.myfitnessapp.dto.request.RutinaRequestDto;
import com.myfitnessapp.dto.response.RutinaResponseDto;
import com.myfitnessapp.repositories.RutinaRepo;
import com.myfitnessapp.validation.ObjectsValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RutinaService{
  private final RutinaRepo rutinaRepo;
  private final ObjectsValidator<RutinaRequestDto> rutinaValidator;
  private final ItemRutinaService itemRutinaService;



  // Mapping methods
  public Rutina toRutina(RutinaRequestDto rDto) {
    List<ItemRutina> itemRutinaList = rDto.getItems().stream().map(itemRutinaService::saveItemRutina).collect(Collectors.toList());
    return new Rutina(rDto.getNombre(),rDto.getDescripcion(),itemRutinaList);
  }

  public RutinaResponseDto toRutinaResponseDto(Rutina rutina) {
    return new RutinaResponseDto(rutina.getId(), rutina.getNombre(), rutina.getDescripcion());
  }
  //
  public RutinaResponseDto saveRutina(RutinaRequestDto rutinaRequestDto) {
    rutinaValidator.validate(rutinaRequestDto);

    Rutina rutina = rutinaRepo.save(toRutina(rutinaRequestDto));
    return new RutinaResponseDto(rutina.getId(), rutina.getNombre(), rutina.getDescripcion());
  }

  public RutinaResponseDto findRutinaById(Integer id) {
    Rutina rutina = rutinaRepo.findById(id).orElse(null);
    return rutina != null ? toRutinaResponseDto(rutina) : null ;
  }

  public RutinaResponseDto deleteRutinaById(Integer id) {
    Rutina rutina = rutinaRepo.findById(id).orElse(null);
    rutinaRepo.deleteById(id);
    return rutina != null ? toRutinaResponseDto(rutina) : null;
  }

  public List<RutinaResponseDto> getRutinas() {
    return rutinaRepo.findAll().stream().map(this::toRutinaResponseDto).toList();
  }

}
