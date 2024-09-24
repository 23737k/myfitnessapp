package com.myfitnessapp.services;

import com.myfitnessapp.dto.request.ItemRutinaReq;
import com.myfitnessapp.dto.request.SerieReq;
import com.myfitnessapp.dto.response.EjercicioRes;
import com.myfitnessapp.dto.response.ItemRutinaRes;
import com.myfitnessapp.dto.response.SerieRes;
import com.myfitnessapp.model.ejercicio.Ejercicio;
import com.myfitnessapp.model.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.model.rutina.ItemRutina;
import com.myfitnessapp.model.rutina.Rutina;
import com.myfitnessapp.model.series.*;
import com.myfitnessapp.repositories.ItemRutinaRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRutinaService {
  private final ItemRutinaRepo itemRutinaRepo;
  private final EjercicioService ejercicioService;
  private final RutinaService rutinaService;

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

  public ItemRutinaRes toItemRutinaRes(ItemRutina itemRutina){
    List<SerieRes> series =  obtenerSeriesRes(itemRutina);
    EjercicioRes ejercicioRes = ejercicioService.toEjercicioRes(ejercicioService.findEjercicioById(itemRutina.getEjercicio().getId()));
    return new ItemRutinaRes(itemRutina.getId(),ejercicioRes,itemRutina.getDescansoEnSeg(),
        itemRutina.getNota(), series);
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
    return items.stream().map(this::toItemRutinaRes).collect(Collectors.toList());
  }

  public List<SerieRes> obtenerSeriesRes(ItemRutina itemRutina){
    TipoDeEjercicio tipoDeEjercicio = itemRutina.getEjercicio().getTipoDeEjercicio();
    List<SerieRes> seriesDto = new ArrayList<>();
    for (Serie s : itemRutina.getSeries()) {
      switch (tipoDeEjercicio){
        case PESO_Y_REPETICIONES :
          seriesDto.add(SerieRes.builder()
                          .id(s.getId())
                  .reps(((PesoYReps) s).getReps())
                  .pesoEnKg(((PesoYReps) s).getPesoEnKg())
                  .build());
          break;
        case PESO_CORPORAL:
          seriesDto.add(SerieRes.builder()
                  .id(s.getId())
                  .reps(((PesoCorpYReps) s).getReps())
                  .build());
          break;
        case PESO_CORPORAL_CON_PESO_EXTRA:
          seriesDto.add(SerieRes.builder()
                  .id(s.getId())
                  .reps(((PesoCorpPesoExtra) s).getReps())
                  .pesoEnKg(((PesoCorpPesoExtra) s).getPesoEnKg())
                  .build());
          break;
        case PESO_CORPORAL_ASISTIDO:
          seriesDto.add(SerieRes.builder()
                  .id(s.getId())
              .reps(((PesoCorpAsistido)s).getReps())
              .pesoEnKg(((PesoCorpAsistido) s).getPesoEnKg())
              .build());
          break;
        case DISTANCIA_Y_DURACION:
          seriesDto.add(SerieRes.builder()
                  .id(s.getId())
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
                  .id(s.getId())
              .tiempoEnSeg(((Duracion) s).getTiempoEnSeg())
              .build());
          break;
      }
    }
    return seriesDto;
  }


  public ItemRutinaRes agregarItem(Integer rutinaId, ItemRutinaReq itemRutinaReq){
    ItemRutina itemRutina = itemRutinaRepo.save(toItemRutina(itemRutinaReq));
    rutinaService.agregarItem(rutinaId, itemRutina);
    return  toItemRutinaRes(itemRutina);
  }

  public void eliminarItem(Integer rutinaId, Integer itemId){
    rutinaService.findById(rutinaId);
    findById(itemId);
    itemRutinaRepo.deleteById(itemId);
  }

//  public void cambiarOrdenItem(Integer rutinaId ,CambiarOrdenItemRequest cambiarOrdenItemRequest) {
//    Rutina rutina = rutinaRepo.findById(rutinaId).orElseThrow(()-> new EntityNotFoundException("Rutina no encontrada"));
//    Integer itemId = cambiarOrdenItemRequest.getItemId();
//    ItemRutina itemRutina = rutina.getItems().stream()
//        .filter(i->i.getId().equals(itemId)).findFirst().orElseThrow(()-> new InvalidReferenceException("Item de rutina no encontrada"));
//
//    Integer itemsSize = rutina.getItems().size();
//    Integer nuevoIndice = cambiarOrdenItemRequest.getIndice();
//    if(nuevoIndice > itemsSize ){
//      throw new InvalidReferenceException("Indice no valido.");
//    }
//    rutina.getItems().remove(itemRutina);
//    rutina.getItems().add(nuevoIndice, itemRutina);
//    rutinaRepo.save(rutina);
//  }

  public ItemRutinaRes modificarItem(ItemRutinaReq itemModif, Integer rutinaId, Integer itemId){
    Rutina rutina = rutinaService.findById(rutinaId);
    ItemRutina item = rutina.getItems().stream().filter(i-> i.getId().equals(itemId))
            .findFirst().orElseThrow(()-> new EntityNotFoundException("item no encontrado"));

    if(itemModif.getEjercicioId()!=null) {
      item.setEjercicio(ejercicioService.findEjercicioById(itemModif.getEjercicioId()));
      item.setSeries(new ArrayList<>());
    }
    if(itemModif.getNota()!=null){
      item.setNota(itemModif.getNota());
    }
    if(itemModif.getDescansoEnSeg()!=null)
      item.setDescansoEnSeg(itemModif.getDescansoEnSeg());

    if(itemModif.getSeries()!=null) {
      List<Serie> series = new ArrayList<>();
      for (SerieReq serie : itemModif.getSeries()){
        series.add(crearSerieFromTipoDeEjercicio(item.getEjercicio().getTipoDeEjercicio(),serie));
      }
      item.setSeries(series);
    }
    return toItemRutinaRes(itemRutinaRepo.save(item));
  }

  public ItemRutina findById(Integer itemRutinaId){
    return itemRutinaRepo.findById(itemRutinaId).orElseThrow(()->(new EntityNotFoundException("item no encontrado")));
  }
}
