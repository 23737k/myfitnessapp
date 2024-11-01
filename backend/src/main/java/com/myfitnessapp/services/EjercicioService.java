package com.myfitnessapp.services;

import com.myfitnessapp.dto.request.EjercicioReq;
import com.myfitnessapp.dto.response.EjercicioRes;
import com.myfitnessapp.exceptions.InvalidReferenceException;
import com.myfitnessapp.model.ejercicio.Ejercicio;
import com.myfitnessapp.repositories.EjercicioRepo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EjercicioService {
  private final EjercicioRepo ejercicioRepo;

  public Ejercicio saveEjercicio(EjercicioReq ejercicioReq) {
    return ejercicioRepo.save(
            new Ejercicio(
                    ejercicioReq.getNombre(),
                    ejercicioReq.getGrupoMuscularPrimario(),
                    ejercicioReq.getGrupoMuscularSecundario(),
                    ejercicioReq.getTipoDeEjercicio()));
  }


  public Ejercicio findEjercicioById(Integer id){
    return ejercicioRepo.findById(id).orElseThrow(()->new InvalidReferenceException("El ejercicio no existe " + id));
  }

  public List<EjercicioRes> listarEjercicios(){
    return ejercicioRepo.findAll().stream().map(this::toEjercicioRes).toList();
  }

  public void eliminarEjercicio(Integer id){
    ejercicioRepo.findById(id).orElseThrow(()->new InvalidReferenceException("El ejercicio no existe " + id));
    ejercicioRepo.deleteById(id);
  }


  public EjercicioRes toEjercicioRes(Ejercicio ejercicio){
    return EjercicioRes.builder()
        .id(ejercicio.getId())
        .nombre(ejercicio.getNombre())
        .grupoMuscularPrimario(ejercicio.getGrupoMuscularPrimario())
        .grupoMuscularSecundario(ejercicio.getGrupoMuscularSecundario())
        .tipoDeEjercicio(ejercicio.getTipoDeEjercicio())
        .build();
  }


}
