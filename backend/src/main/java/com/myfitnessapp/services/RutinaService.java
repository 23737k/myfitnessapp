package com.myfitnessapp.services;

import com.myfitnessapp.dto.request.RutinaReq;
import com.myfitnessapp.dto.response.RutinaRes;
import com.myfitnessapp.model.rutina.ItemRutina;
import com.myfitnessapp.model.rutina.Rutina;
import com.myfitnessapp.repositories.RutinaRepo;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RutinaService{
  private final RutinaRepo rutinaRepo;

  public RutinaRes saveRutina(RutinaReq rutinaReq) {
    return toRutinaRes(rutinaRepo.save(toRutina(rutinaReq)));
  }

  public RutinaRes findRutinaById(Integer id) {
    Rutina rutina = rutinaRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Rutina no encontrada"));
    return toRutinaRes(rutina);
  }

  public Rutina findById(Integer id){
    return rutinaRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Rutina no encontrada"));
  }

  @Transactional
  public void deleteRutinaById(Integer id) {
        rutinaRepo.deleteById(id);
  }

  public List<RutinaRes> getRutinas() {
    return rutinaRepo.findAll().stream().map(this::toRutinaRes).toList();
  }

  public void agregarItem(Integer rutinaId, ItemRutina item){
    Rutina rutina = findById(rutinaId);
    rutina.getItems().add(item);
    rutinaRepo.save(rutina);
  }


  // Mapping methods
  public Rutina toRutina(RutinaReq rDto) {
    return new Rutina(rDto.getNombre(),rDto.getDescripcion(),new ArrayList<>());
  }

  public RutinaRes toRutinaRes(Rutina rutina) {
    return new RutinaRes(rutina.getId(), rutina.getNombre(), rutina.getDescripcion());
  }
  //



}
