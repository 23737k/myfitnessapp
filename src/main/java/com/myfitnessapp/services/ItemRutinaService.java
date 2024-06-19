package com.myfitnessapp.services;

import com.myfitnessapp.dominio.ejercicio.Ejercicio;
import com.myfitnessapp.dominio.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.dominio.rutina.ItemRutina;
import com.myfitnessapp.dominio.series.Serie;
import com.myfitnessapp.dominio.series.SeriePesoCorpYReps;
import com.myfitnessapp.dominio.series.SeriePesoYReps;
import com.myfitnessapp.dominio.series.SerieTiempo;
import com.myfitnessapp.dto.request.ItemRutinaRequestDto;
import com.myfitnessapp.dto.request.SerieRequestDto;
import com.myfitnessapp.dto.response.ItemRutinaResponseDto;
import com.myfitnessapp.exceptions.SerieNoValidaException;
import com.myfitnessapp.repositories.ItemRutinaRepo;
import com.myfitnessapp.validation.ObjectsValidator;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemRutinaService {
  private final ItemRutinaRepo itemRutinaRepo;
  private final EjercicioService ejercicioService;
  private final ObjectsValidator<ItemRutinaRequestDto> validator;

  public ItemRutina toItemRutina(ItemRutinaRequestDto itemRutinaRequestDto) {
    validator.validate(itemRutinaRequestDto);
    Ejercicio ejercicio = ejercicioService.findEjercicioById(itemRutinaRequestDto.getEjercicioId());
    List<Serie> series = new ArrayList<>();

    for(SerieRequestDto serieDto : itemRutinaRequestDto.getSeries()) {
      series.add(crearSerieFromTipoDeEjercicio(ejercicio.getTipoDeEjercicio(),serieDto));
    }

      return ItemRutina.builder()
        .ejercicio(ejercicio)
        .descansoEnSeg(itemRutinaRequestDto.getDescansoEnSeg())
        .nota(itemRutinaRequestDto.getNota())
        .series(series)
        .build();
  }

  public Serie crearSerieFromTipoDeEjercicio(TipoDeEjercicio tipoDeEjercicio, SerieRequestDto serieRequestDto) {
    switch (tipoDeEjercicio) {
      case PESO_Y_REPETICIONES:
        SeriePesoYReps seriePesoYReps = new SeriePesoYReps(serieRequestDto.getReps(), serieRequestDto.getPesoEnKg());
        new ObjectsValidator<SeriePesoYReps>().validate(seriePesoYReps);
        return seriePesoYReps;
      case TIEMPO:
        SerieTiempo serieTiempo = new SerieTiempo(serieRequestDto.getTiempoEnSeg());
        new ObjectsValidator<SerieTiempo>().validate(serieTiempo);
        return serieTiempo;
      case PESO_CORPORAL :
        SeriePesoCorpYReps seriePesoCorpYReps = new SeriePesoCorpYReps(serieRequestDto.getReps());
        new ObjectsValidator<SeriePesoCorpYReps>().validate(seriePesoCorpYReps);
        return seriePesoCorpYReps;
      default :
        throw new SerieNoValidaException("Formato de serie invalido");
    }
  }

  public List<ItemRutinaResponseDto> getItems(Integer rutinaId){
    List<ItemRutina> items = itemRutinaRepo.findAllByRutina(rutinaId);
    return items.stream().map(this::toResponseDto).collect(Collectors.toList());
  }

  public ItemRutinaResponseDto toResponseDto(ItemRutina itemRutina){
    return new ItemRutinaResponseDto(itemRutina.getId(),itemRutina.getEjercicio().getNombre(),itemRutina.getDescansoEnSeg(),
            itemRutina.getNota());
  }

}
