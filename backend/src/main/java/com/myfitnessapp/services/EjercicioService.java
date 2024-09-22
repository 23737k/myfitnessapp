package com.myfitnessapp.services;

import com.myfitnessapp.model.ejercicio.Ejercicio;
import com.myfitnessapp.model.ejercicio.GrupoMuscular;
import com.myfitnessapp.model.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.dto.request.EjercicioReq;
import com.myfitnessapp.exceptions.InvalidReferenceException;
import com.myfitnessapp.repositories.EjercicioRepo;
import com.myfitnessapp.repositories.GrupoMuscularRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EjercicioService {
  private final EjercicioRepo ejercicioRepo;
  private final GrupoMuscularRepo grupoMuscularRepo;

  public Ejercicio saveEjercicio(EjercicioReq ejDto) {

    GrupoMuscular gmPrimario = grupoMuscularRepo.
        findById(ejDto.getGrupoMuscularPrimario()).orElseThrow(() -> new InvalidReferenceException("Grupo muscular secundario no encontrado"));
    GrupoMuscular gmSecundario = null;
    TipoDeEjercicio tipoDeEjercicio;

    if (ejDto.getGrupoMuscularSecundario() != null) {
      gmSecundario = grupoMuscularRepo.findById(ejDto.getGrupoMuscularSecundario()).orElseThrow(() -> new InvalidReferenceException("Grupo muscular secundario no encontrado"));
    }

    try{
      tipoDeEjercicio = TipoDeEjercicio.valueOf(ejDto.getTipoDeEjercicio().toUpperCase());
    }
    catch(IllegalArgumentException e){
      throw new InvalidReferenceException("Tipo de ejercicio invalido " + ejDto.getTipoDeEjercicio());
    }

    return ejercicioRepo.save(new Ejercicio(ejDto.getNombre(),gmPrimario, gmSecundario,tipoDeEjercicio));
  }


  public Ejercicio findEjercicioById(Integer id){
    return ejercicioRepo.findById(id).orElseThrow(()->new InvalidReferenceException("El ejercicio no existe " + id));
  }

  public Page<Ejercicio> listarEjercicios(Pageable pageable){
    return ejercicioRepo.findAll(pageable);
  }

  public void eliminarEjercicio(Integer id){
    ejercicioRepo.findById(id).orElseThrow(()->new InvalidReferenceException("El ejercicio no existe " + id));
    ejercicioRepo.deleteById(id);
  }



}
