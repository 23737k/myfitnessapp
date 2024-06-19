package com.myfitnessapp.utils;

import com.myfitnessapp.dominio.ejercicio.GrupoMuscular;
import com.myfitnessapp.dominio.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.dto.request.EjercicioRequestDto;
import com.myfitnessapp.repositories.GrupoMuscularRepo;
import com.myfitnessapp.services.EjercicioService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap {
  private final GrupoMuscularRepo grupoMuscularRepo;
  private final EjercicioService ejercicioService;

  @Transactional
  public void init(){
    cargarGruposMusculares();
    cargarEjercicios();
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
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Press banca",1,2,pesoYReps ));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Sentadilla", 9, 13, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Dominadas", 3, null,pesoYReps ));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Curl de bíceps", 4, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Press militar", 6, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Remo con barra", 3, 5, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Peso muerto", 9, 11, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Extensiones de tríceps", 2, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Elevaciones laterales", 6, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Encogimientos de hombros", 5, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Abdominales en banco declinado", 8, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Press inclinado con mancuernas", 1, 2, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Curl de muñeca", 7, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Prensa de piernas", 9, 12, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Elevaciones de talones", 10, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Femoral acostado", 11, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Hip thrust", 13, 9, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Sentadilla búlgara", 9, 13, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Press francés", 2, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Remo con mancuerna", 3, 4, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Face pull", 5, 6, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Levantamiento frontal", 6, null, pesoYReps));
    ejercicioService.saveEjercicio(new EjercicioRequestDto("Fondos en paralelas", 1, 2, pesoYReps));
  }


}
