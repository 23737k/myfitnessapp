package com.myfitnessapp.utils;

import com.myfitnessapp.dto.request.EjercicioReq;
import com.myfitnessapp.model.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.services.EjercicioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import static com.myfitnessapp.model.ejercicio.GrupoMuscular.*;
import static com.myfitnessapp.model.ejercicio.TipoDeEjercicio.*;

@Component
@RequiredArgsConstructor
public class Bootstrap {
  private final EjercicioService ejercicioService;
  private final EntityManager em;

  @Transactional
  public void init(){
    if(bdVacia()) {
      cargarEjercicios();
    }

  }

  @Transactional
  public void cargarEjercicios(){
    String pesoYReps = PESO_Y_REPETICIONES.toString();
    String pesoCorp = TipoDeEjercicio.PESO_CORPORAL.toString();
    String pesoCorpYPesoExtra = TipoDeEjercicio.PESO_CORPORAL_CON_PESO_EXTRA.toString();


    ejercicioService.saveEjercicio(new EjercicioReq("Press banca", PECTORAL,TRICEPS, PESO_Y_REPETICIONES ));
    ejercicioService.saveEjercicio(new EjercicioReq("Sentadilla", CUADRICEPS, GLUTEOS, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Dominadas", DORSALES, BICEPS,PESO_CORPORAL ));
    ejercicioService.saveEjercicio(new EjercicioReq("Curl de bíceps", BICEPS, ANTEBRAZOS, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Press militar", DELTOIDES, TRICEPS, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Remo con barra",DORSALES, BICEPS, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Peso muerto", CUADRICEPS, DORSALES, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Extensiones de tríceps", TRICEPS, null, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Elevaciones laterales", DELTOIDES, null, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Encogimientos de hombros", TRAPECIOS, null, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Abdominales en banco declinado", ABDOMINALES, null, PESO_CORPORAL));
    ejercicioService.saveEjercicio(new EjercicioReq("Press inclinado con mancuernas", PECTORAL, DELTOIDES, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Curl de muñeca", ANTEBRAZOS, null, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Prensa de piernas", CUADRICEPS, GLUTEOS, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Elevaciones de talones", GEMELOS, null, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Femoral acostado", ISQUIOTIBIALES, null, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Hip thrust", GLUTEOS, null, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Sentadilla búlgara", CUADRICEPS, GLUTEOS, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Press francés", TRICEPS, null, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Remo con mancuerna", DORSALES, BICEPS, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Face pull", TRICEPS, DELTOIDES, PESO_Y_REPETICIONES));
    ejercicioService.saveEjercicio(new EjercicioReq("Fondos en paralelas", TRICEPS, PECTORAL, PESO_CORPORAL_CON_PESO_EXTRA));
  }

  private boolean bdVacia(){
    List<String> tablas = List.of("Ejercicio");
      for(String t : tablas){
        Query query = em.createQuery("SELECT COUNT(*) FROM " + t);
        Long count = (Long) query.getSingleResult();
        if(count>0)
          return false;
      }
      return true;
  }

}
