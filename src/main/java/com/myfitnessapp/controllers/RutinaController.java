package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.RutinaRequestDto;
import com.myfitnessapp.dto.response.RutinaResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myfitnessapp.services.RutinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rutinas")
@RequiredArgsConstructor
public class RutinaController {
  private final RutinaService rutinaService;

  @PostMapping
  public ResponseEntity<?> crearRutina(@RequestBody @Validated RutinaRequestDto rutina) {
    RutinaResponseDto rutinaResponseDto = rutinaService.saveRutina(rutina);
    return new ResponseEntity<>(rutinaResponseDto, HttpStatus.CREATED);
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
