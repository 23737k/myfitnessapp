package com.myfitnessapp.services;

import com.myfitnessapp.model.ejercicio.Ejercicio;
import com.myfitnessapp.model.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.model.rutina.ItemRutina;
import com.myfitnessapp.model.series.DistanciaYDuracion;
import com.myfitnessapp.model.series.DistanciaYPeso;
import com.myfitnessapp.model.series.Duracion;
import com.myfitnessapp.model.series.PesoCorpAsistido;
import com.myfitnessapp.model.series.PesoCorpPesoExtra;
import com.myfitnessapp.model.series.PesoCorpYReps;
import com.myfitnessapp.model.series.PesoYReps;
import com.myfitnessapp.model.series.Serie;
import com.myfitnessapp.dto.request.ItemRutinaReq;
import com.myfitnessapp.dto.request.SerieReq;
import com.myfitnessapp.dto.response.ItemRutinaRes;
import com.myfitnessapp.dto.response.SerieRes;
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

  public ItemRutina toItemRutina(ItemRutinaReq itemRutinaReq) {
    Ejercicio ejercicio = ejercicioService.findEjercicioById(itemRutinaReq.getEjercicioId());
    List<Serie> series = new ArrayList<>();

    for(SerieReq serieDto : itemRutinaReq.getSeries()) {
      series.add(crearSerieFromTipoDeEjercicio(ejercicio.getTipoDeEjercicio(),serieDto));
    }

      return ItemRutina.builder()
        .ejercicio(ejercicio)
        .descansoEnSeg(itemRutinaReq.getDescansoEnSeg())
        .nota(itemRutinaReq.getNota())
        .series(series)
        .build();
  }

  public Serie crearSerieFromTipoDeEjercicio(TipoDeEjercicio tipoDeEjercicio, SerieReq serieReq) {
    return switch (tipoDeEjercicio) {
      case PESO_Y_REPETICIONES ->
          new PesoYReps(serieReq.getReps(), serieReq.getPesoEnKg());
      case DURACION -> new Duracion(serieReq.getTiempoEnSeg());
      case PESO_CORPORAL -> new PesoCorpYReps(serieReq.getReps());
      case PESO_CORPORAL_CON_PESO_EXTRA ->
          new PesoCorpPesoExtra(serieReq.getReps(), serieReq.getPesoEnKg());
      case PESO_CORPORAL_ASISTIDO ->
          new PesoCorpAsistido(serieReq.getReps(), serieReq.getPesoEnKg());
      case DISTANCIA_Y_PESO ->
          new DistanciaYPeso(serieReq.getDistancia(), serieReq.getPesoEnKg());
      case DISTANCIA_Y_DURACION ->
          new DistanciaYDuracion(serieReq.getDistancia(), serieReq.getTiempoEnSeg());
    };
  }

  public List<ItemRutinaRes> getItems(Integer rutinaId){
    List<ItemRutina> items = itemRutinaRepo.findAllByRutina(rutinaId);
    return items.stream().map(this::toItemResponseDto).collect(Collectors.toList());
  }

  public List<SerieRes> obtenerSeriesResponseDto(ItemRutina itemRutina){
    TipoDeEjercicio tipoDeEjercicio = itemRutina.getEjercicio().getTipoDeEjercicio();
    List<SerieRes> seriesDto = new ArrayList<>();
    for (Serie s : itemRutina.getSeries()) {
      switch (tipoDeEjercicio){
        case PESO_Y_REPETICIONES :
          seriesDto.add(SerieRes.builder()
                  .reps(((PesoYReps) s).getReps())
                  .pesoEnKg(((PesoYReps) s).getPesoEnKg())
                  .build());
          break;
        case PESO_CORPORAL:
          seriesDto.add(SerieRes.builder()
                  .reps(((PesoCorpYReps) s).getReps())
                  .build());
          break;
        case PESO_CORPORAL_CON_PESO_EXTRA:
          seriesDto.add(SerieRes.builder()
                  .reps(((PesoCorpPesoExtra) s).getReps())
                  .pesoEnKg(((PesoCorpPesoExtra) s).getPesoEnKg())
                  .build());
          break;
        case PESO_CORPORAL_ASISTIDO:
          seriesDto.add(SerieRes.builder()
              .reps(((PesoCorpAsistido)s).getReps())
              .pesoEnKg(((PesoCorpAsistido) s).getPesoEnKg())
              .build());
          break;
        case DISTANCIA_Y_DURACION:
          seriesDto.add(SerieRes.builder()
              .distancia(((DistanciaYDuracion) s).getDistancia())
              .tiempoEnSeg(((DistanciaYDuracion) s).getTiempoEnSeg())
              .build());
          break;
        case DISTANCIA_Y_PESO:
          seriesDto.add(SerieRes.builder()
              .distancia(((DistanciaYPeso) s).getDistancia())
              .pesoEnKg(((DistanciaYPeso) s).getPesoEnKg())
              .build());
          break;
        case DURACION:
          seriesDto.add(SerieRes.builder()
              .tiempoEnSeg(((Duracion) s).getTiempoEnSeg())
              .build());
          break;
      }
    }
    return seriesDto;
  }


  public ItemRutinaRes toItemResponseDto(ItemRutina itemRutina){
    List<SerieRes> series =  obtenerSeriesResponseDto(itemRutina);
        return new ItemRutinaRes(itemRutina.getId(),itemRutina.getEjercicio().getNombre(),itemRutina.getDescansoEnSeg(),
            itemRutina.getNota(), series);
  }

}
