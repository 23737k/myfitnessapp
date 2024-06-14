package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.RutinaRequestDto;
import com.myfitnessapp.dto.response.RutinaResponseDto;
import com.myfitnessapp.services.RutinaService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rutinas")
public class RutinaController {
  private final RutinaService rutinaService;

  public RutinaController(RutinaService rutinaService) {
    this.rutinaService = rutinaService;
  }

  @PostMapping
  public RutinaResponseDto crearRutina(@Valid @RequestBody RutinaRequestDto rutina) {
    return rutinaService.saveRutina(rutina);
  }

  @GetMapping
  public Map<String, List<RutinaResponseDto>> listarRutinas() {
    Map<String, List<RutinaResponseDto>> rutinas = new HashMap<>();
    rutinas.put("rutinas", rutinaService.getRutinas());
    return rutinas ;
  }

  @GetMapping("/{id}")
  public RutinaResponseDto getRutinaById(@PathVariable int id) {
    return rutinaService.findRutinaById(id);
  }

  @DeleteMapping("{id}")
  public RutinaResponseDto eliminarRutina(@PathVariable int id) {
    return rutinaService.deleteRutinaById(id);
  }
}
