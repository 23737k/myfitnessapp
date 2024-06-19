package com.myfitnessapp.services;

import com.myfitnessapp.dominio.ejercicio.Ejercicio;
import com.myfitnessapp.dominio.ejercicio.GrupoMuscular;
import com.myfitnessapp.dominio.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.dto.request.EjercicioRequestDto;
import com.myfitnessapp.repositories.EjercicioRepo;
import com.myfitnessapp.repositories.GrupoMuscularRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EjercicioService {
  private final EjercicioRepo ejercicioRepo;
  private final GrupoMuscularRepo grupoMuscularRepo;

  public Ejercicio saveEjercicio(EjercicioRequestDto ejDto) {

    GrupoMuscular gmPrimario = grupoMuscularRepo.findById(ejDto.getGrupoMuscularPrimario()).orElseThrow(() -> new IllegalArgumentException("Grupo muscular secundario no encontrado"));
    GrupoMuscular gmSecundario = null;
    TipoDeEjercicio tipoDeEjercicio;

    if (ejDto.getGrupoMuscularSecundario() != null) {
      gmSecundario = grupoMuscularRepo.findById(ejDto.getGrupoMuscularSecundario()).orElseThrow(() -> new IllegalArgumentException("Grupo muscular secundario no encontrado"));
    }

    try{
      tipoDeEjercicio = TipoDeEjercicio.valueOf(ejDto.getTipoDeEjercicio().toUpperCase());
    }
    catch(IllegalArgumentException e){
      throw new RuntimeException("Tipo de ejercicio invalido " + ejDto.getTipoDeEjercicio());
    }

    return ejercicioRepo.save(new Ejercicio(ejDto.getNombre(),gmPrimario, gmSecundario,tipoDeEjercicio));
  }


  public Ejercicio findEjercicioById(Integer id){
    return ejercicioRepo.findById(id).orElseThrow(()->new EntityNotFoundException("El ejercicio no existe" + id));
  }



}
