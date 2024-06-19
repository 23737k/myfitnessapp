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
import com.myfitnessapp.repositories.ItemRutinaRepo;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemRutinaService {
  @Lazy
  private final ItemRutinaRepo itemRutinaRepo;
  private final EjercicioService ejercicioService;

  public ItemRutina toItemRutina(ItemRutinaRequestDto itemRutinaRequestDto) {
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
    return switch (tipoDeEjercicio) {
      case PESO_Y_REPETICIONES -> new SeriePesoYReps(serieRequestDto.getReps(), serieRequestDto.getPesoEnKg());
      case TIEMPO -> new SerieTiempo(serieRequestDto.getTiempoEnSeg());
      case PESO_CORPORAL -> new SeriePesoCorpYReps(serieRequestDto.getReps());
      default -> null;
    };
  }

  public List<ItemRutinaResponseDto> getItems(Integer rutinaId){
    List<ItemRutina> items = itemRutinaRepo.findAllByRutina(rutinaId);
    List<ItemRutinaResponseDto> itemsDto = new ArrayList<>();
    items.forEach(i-> itemsDto.add(new ItemRutinaResponseDto(i.getId(),i.getEjercicio().getNombre(),
            i.getDescansoEnSeg(),i.getNota())));
    return itemsDto;
  }

  public ItemRutinaResponseDto toResponseDto(ItemRutina itemRutina){
    return new ItemRutinaResponseDto(itemRutina.getId(),itemRutina.getEjercicio().getNombre(),itemRutina.getDescansoEnSeg(),
            itemRutina.getNota());
  }

}
