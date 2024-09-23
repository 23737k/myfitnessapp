package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.RutinaReq;
import com.myfitnessapp.dto.response.RutinaRes;
import com.myfitnessapp.services.RutinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutinas")
@RequiredArgsConstructor
public class RutinaController {
  private final RutinaService rutinaService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RutinaRes crearRutina(@RequestBody @Validated RutinaReq rutina) {
    return rutinaService.saveRutina(rutina);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<RutinaRes> listarRutinas() {
    return rutinaService.getRutinas();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public RutinaRes getRutinaById(@PathVariable int id) {
    return rutinaService.findRutinaById(id);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void eliminarRutina(@PathVariable int id) {
    rutinaService.deleteRutinaById(id);
  }


}
