package com.myfitnessapp.services;

import com.myfitnessapp.dominio.ejercicio.Ejercicio;
import com.myfitnessapp.dominio.ejercicio.TipoDeEjercicio;
import com.myfitnessapp.dominio.rutina.ItemRutina;
import com.myfitnessapp.dominio.series.Serie;
import com.myfitnessapp.dominio.series.SeriePesoCorpYReps;
import com.myfitnessapp.dominio.series.SeriePesoYReps;
import com.myfitnessapp.dominio.series.SerieTiempo;
import com.myfitnessapp.dto.request.ItemRutinaRequestDto;
import com.myfitnessapp.dto.request.SerieRequestDto;
import com.myfitnessapp.repositories.ItemRutinaRepo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemRutinaService {
  private final ItemRutinaRepo itemRutinaRepo;
  private final EjercicioService ejercicioService;

  public ItemRutina saveItemRutina(ItemRutinaRequestDto itemRutinaRequestDto) {
    Ejercicio ejercicio = ejercicioService.findEjercicioById(itemRutinaRequestDto.getEjercicioId());
    List<Serie> series = new ArrayList<>();

    for(SerieRequestDto serieDto : itemRutinaRequestDto.getSeries()) {
      series.add(crearSerieFromTipoDeEjercicio(ejercicio.getTipoDeEjercicio(),serieDto));
    }

    ItemRutina itemRutina = ItemRutina.builder()
        .ejercicio(ejercicio)
        .descansoEnSeg(itemRutinaRequestDto.getDescansoEnSeg())
        .nota(itemRutinaRequestDto.getNota())
        .series(series)
        .build();
    return itemRutinaRepo.save(itemRutina);
  }

  public Serie crearSerieFromTipoDeEjercicio(TipoDeEjercicio tipoDeEjercicio, SerieRequestDto serieRequestDto) {
    return switch (tipoDeEjercicio) {
      case PESO_Y_REPETICIONES -> new SeriePesoYReps(serieRequestDto.getReps(), serieRequestDto.getPesoEnKg());
      case TIEMPO -> new SerieTiempo(serieRequestDto.getTiempoEnSeg());
      case PESO_CORPORAL -> new SeriePesoCorpYReps(serieRequestDto.getReps());
      default -> null;
    };
  }
}
