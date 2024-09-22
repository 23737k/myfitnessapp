package com.myfitnessapp.services;

import com.myfitnessapp.dto.response.ItemRutinaRes;
import com.myfitnessapp.model.rutina.ItemRutina;
import com.myfitnessapp.model.rutina.Rutina;
import com.myfitnessapp.model.series.Serie;
import com.myfitnessapp.dto.request.CambiarOrdenItemRequest;
import com.myfitnessapp.dto.request.ItemRutinaReq;
import com.myfitnessapp.dto.request.RutinaReq;
import com.myfitnessapp.dto.request.SerieReq;
import com.myfitnessapp.dto.response.RutinaRes;
import com.myfitnessapp.exceptions.InvalidReferenceException;
import com.myfitnessapp.repositories.RutinaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RutinaService{
  private final RutinaRepo rutinaRepo;
  private final ItemRutinaService itemRutinaService;
  private final EjercicioService ejercicioService;

  // Mapping methods
  public Rutina toRutina(RutinaReq rDto) {
    List<ItemRutina> itemRutinaList = rDto.getItems().stream().map(itemRutinaService::toItemRutina).collect(Collectors.toList());
    return new Rutina(rDto.getNombre(),rDto.getDescripcion(),itemRutinaList);
  }

  public RutinaRes toRutinaRes(Rutina rutina) {
    return new RutinaRes(rutina.getId(), rutina.getNombre(), rutina.getDescripcion());
  }
  //

  public RutinaRes saveRutina(RutinaReq rutinaReq) {
    Rutina rutina = rutinaRepo.save(toRutina(rutinaReq));
    return new RutinaRes(rutina.getId(), rutina.getNombre(), rutina.getDescripcion());
  }

  public RutinaRes findRutinaById(Integer id) {
    Rutina rutina = rutinaRepo.findById(id).orElse(null);
    return rutina != null ? toRutinaRes(rutina) : null ;
  }

  public RutinaRes deleteRutinaById(Integer id) {
    Rutina rutina = rutinaRepo.findById(id).orElse(null);
    rutinaRepo.deleteById(id);
    return rutina != null ? toRutinaRes(rutina) : null;
  }

  public List<RutinaRes> getRutinas() {
    return rutinaRepo.findAll().stream().map(this::toRutinaRes).toList();
  }

  public ItemRutinaRes agregarItem(Integer rutinaId, ItemRutina item){
    Rutina rutina = rutinaRepo.findById(rutinaId).orElseThrow(()-> new EntityNotFoundException("Rutina no encontrada"));
    rutina.getItems().add(item);
    return toItemR(rutinaRepo.save(rutina));
  }

  public void eliminarItem(Integer rutinaId, Integer itemId){
    Rutina rutina = rutinaRepo.findById(rutinaId).orElseThrow(()-> new EntityNotFoundException("La rutina no existe"));
    boolean itemEliminado = rutina.getItems().removeIf(i -> i.getId().equals(itemId));
    if(!itemEliminado)
      throw new EntityNotFoundException("El item no existe");
    rutinaRepo.save(rutina);
  }

  public void cambiarOrdenItem(Integer rutinaId ,CambiarOrdenItemRequest cambiarOrdenItemRequest) {
    Rutina rutina = rutinaRepo.findById(rutinaId).orElseThrow(()-> new EntityNotFoundException("Rutina no encontrada"));
    Integer itemId = cambiarOrdenItemRequest.getItemId();
    ItemRutina itemRutina = rutina.getItems().stream()
        .filter(i->i.getId().equals(itemId)).findFirst().orElseThrow(()-> new InvalidReferenceException("Item de rutina no encontrada"));

    Integer itemsSize = rutina.getItems().size();
    Integer nuevoIndice = cambiarOrdenItemRequest.getIndice();
    if(nuevoIndice > itemsSize ){
      throw new InvalidReferenceException("Indice no valido.");
    }
    rutina.getItems().remove(itemRutina);
    rutina.getItems().add(nuevoIndice, itemRutina);
    rutinaRepo.save(rutina);
  }

  public void modificarItem(ItemRutinaReq itemModif, Integer rutinaId, Integer itemId){
    Rutina rutina = rutinaRepo.findById(rutinaId).orElseThrow(()-> new EntityNotFoundException("Rutina no encontrada"));
    ItemRutina item = rutina.getItems().stream().filter(i-> i.getId().equals(itemId)).findFirst()
        .orElseThrow(()-> new InvalidReferenceException("Item de rutina no existe"));

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
        series.add(itemRutinaService.crearSerieFromTipoDeEjercicio(item.getEjercicio().getTipoDeEjercicio(),serie));
      }
      item.setSeries(series);
    }
    rutinaRepo.save(rutina);
  }
}
