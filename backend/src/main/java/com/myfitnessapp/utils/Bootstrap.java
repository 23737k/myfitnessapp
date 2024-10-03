package com.myfitnessapp.utils;

import com.myfitnessapp.dto.request.EjercicioReq;
import com.myfitnessapp.model.ejercicio.GrupoMuscular;
import com.myfitnessapp.model.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.repositories.GrupoMuscularRepo;
import com.myfitnessapp.services.EjercicioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap {
  private final GrupoMuscularRepo grupoMuscularRepo;
  private final EjercicioService ejercicioService;
  private final EntityManager em;

  @Transactional
  public void init(){
    if(bdVacia()) {
      cargarGruposMusculares();
      cargarEjercicios();
    }

  }

  @Transactional
  public void cargarGruposMusculares(){
    List<String> gruposMusculares = List.of("Pectoral", "Triceps", "Dorsales", "Biceps",
        "Trapecios","Deltoides","Antebrazos", "Abdominales", "Cuadriceps", "Gemelos","Isquiotibiales", "Aductores","Gluteos");
    gruposMusculares.forEach(gm -> grupoMuscularRepo.save(new GrupoMuscular(gm)));
  }

  @Transactional
  public void cargarEjercicios(){
    String pesoYReps = TipoDeEjercicio.PESO_Y_REPETICIONES.toString();
    String pesoCorp = TipoDeEjercicio.PESO_CORPORAL.toString();
    String pesoCorpYPesoExtra = TipoDeEjercicio.PESO_CORPORAL_CON_PESO_EXTRA.toString();


    ejercicioService.saveEjercicio(new EjercicioReq("Press banca",1,2,pesoYReps ));
    ejercicioService.saveEjercicio(new EjercicioReq("Sentadilla", 9, 13, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Dominadas", 3, null,pesoCorp ));
    ejercicioService.saveEjercicio(new EjercicioReq("Curl de bíceps", 4, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Press militar", 6, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Remo con barra", 3, 5, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Peso muerto", 9, 11, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Extensiones de tríceps", 2, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Elevaciones laterales", 6, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Encogimientos de hombros", 5, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Abdominales en banco declinado", 8, null, pesoCorp));
    ejercicioService.saveEjercicio(new EjercicioReq("Press inclinado con mancuernas", 1, 2, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Curl de muñeca", 7, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Prensa de piernas", 9, 12, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Elevaciones de talones", 10, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Femoral acostado", 11, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Hip thrust", 13, 9, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Sentadilla búlgara", 9, 13, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Press francés", 2, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Remo con mancuerna", 3, 4, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Face pull", 5, 6, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Levantamiento frontal", 6, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioReq("Fondos en paralelas", 1, 2, pesoCorpYPesoExtra));
  }

  private boolean bdVacia(){
    List<String> tablas = List.of("Ejercicio", "GrupoMuscular");
      for(String t : tablas){
        Query query = em.createQuery("SELECT COUNT(*) FROM " + t);
        Long count = (Long) query.getSingleResult();
        if(count>0)
          return false;
      }
      return true;
  }

}
