package com.myfitnessapp.services;

import com.myfitnessapp.dominio.ejercicio.Ejercicio;
import com.myfitnessapp.dominio.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.dominio.rutina.ItemRutina;
import com.myfitnessapp.dominio.series.DistanciaYDuracion;
import com.myfitnessapp.dominio.series.DistanciaYPeso;
import com.myfitnessapp.dominio.series.Duracion;
import com.myfitnessapp.dominio.series.PesoCorpAsistido;
import com.myfitnessapp.dominio.series.PesoCorpPesoExtra;
import com.myfitnessapp.dominio.series.PesoCorpYReps;
import com.myfitnessapp.dominio.series.PesoYReps;
import com.myfitnessapp.dominio.series.Serie;
import com.myfitnessapp.dto.request.ItemRutinaRequestDto;
import com.myfitnessapp.dto.request.SerieRequestDto;
import com.myfitnessapp.dto.response.ItemRutinaResponseDto;
import com.myfitnessapp.dto.response.SerieResponseDto;
import com.myfitnessapp.repositories.ItemRutinaRepo;
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
      case PESO_Y_REPETICIONES ->
          new PesoYReps(serieRequestDto.getReps(), serieRequestDto.getPesoEnKg());
      case DURACION -> new Duracion(serieRequestDto.getTiempoEnSeg());
      case PESO_CORPORAL -> new PesoCorpYReps(serieRequestDto.getReps());
      case PESO_CORPORAL_CON_PESO_EXTRA ->
          new PesoCorpPesoExtra(serieRequestDto.getReps(), serieRequestDto.getPesoEnKg());
      case PESO_CORPORAL_ASISTIDO ->
          new PesoCorpAsistido(serieRequestDto.getReps(), serieRequestDto.getPesoEnKg());
      case DISTANCIA_Y_PESO ->
          new DistanciaYPeso(serieRequestDto.getDistancia(), serieRequestDto.getPesoEnKg());
      case DISTANCIA_Y_DURACION ->
          new DistanciaYDuracion(serieRequestDto.getDistancia(), serieRequestDto.getTiempoEnSeg());
    };
  }

  public List<ItemRutinaResponseDto> getItems(Integer rutinaId){
    List<ItemRutina> items = itemRutinaRepo.findAllByRutina(rutinaId);
    return items.stream().map(this::toItemResponseDto).collect(Collectors.toList());
  }

  public List<SerieResponseDto> obtenerSeriesResponseDto(ItemRutina itemRutina){
    TipoDeEjercicio tipoDeEjercicio = itemRutina.getEjercicio().getTipoDeEjercicio();
    List<SerieResponseDto> seriesDto = new ArrayList<>();
    for (Serie s : itemRutina.getSeries()) {
      switch (tipoDeEjercicio){
        case PESO_Y_REPETICIONES :
          seriesDto.add(SerieResponseDto.builder()
                  .reps(((PesoYReps) s).getReps())
                  .pesoEnKg(((PesoYReps) s).getPesoEnKg())
                  .build());
          break;
        case PESO_CORPORAL:
          seriesDto.add(SerieResponseDto.builder()
                  .reps(((PesoCorpYReps) s).getReps())
                  .build());
          break;
        case PESO_CORPORAL_CON_PESO_EXTRA:
          seriesDto.add(SerieResponseDto.builder()
                  .reps(((PesoCorpPesoExtra) s).getReps())
                  .pesoEnKg(((PesoCorpPesoExtra) s).getPesoEnKg())
                  .build());
          break;
        case PESO_CORPORAL_ASISTIDO:
          seriesDto.add(SerieResponseDto.builder()
              .reps(((PesoCorpAsistido)s).getReps())
              .pesoEnKg(((PesoCorpAsistido) s).getPesoEnKg())
              .build());
          break;
        case DISTANCIA_Y_DURACION:
          seriesDto.add(SerieResponseDto.builder()
              .distancia(((DistanciaYDuracion) s).getDistancia())
              .tiempoEnSeg(((DistanciaYDuracion) s).getTiempoEnSeg())
              .build());
          break;
        case DISTANCIA_Y_PESO:
          seriesDto.add(SerieResponseDto.builder()
              .distancia(((DistanciaYPeso) s).getDistancia())
              .pesoEnKg(((DistanciaYPeso) s).getPesoEnKg())
              .build());
          break;
        case DURACION:
          seriesDto.add(SerieResponseDto.builder()
              .tiempoEnSeg(((Duracion) s).getTiempoEnSeg())
              .build());
          break;
      }
    }
    return seriesDto;
  }


  public ItemRutinaResponseDto toItemResponseDto(ItemRutina itemRutina){
    List<SerieResponseDto> series =  obtenerSeriesResponseDto(itemRutina);
        return new ItemRutinaResponseDto(itemRutina.getId(),itemRutina.getEjercicio().getNombre(),itemRutina.getDescansoEnSeg(),
            itemRutina.getNota(), series);
  }

}
