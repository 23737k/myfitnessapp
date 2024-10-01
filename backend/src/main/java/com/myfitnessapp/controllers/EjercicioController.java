package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.EjercicioReq;
import com.myfitnessapp.dto.response.EjercicioRes;
import com.myfitnessapp.services.EjercicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
@RequiredArgsConstructor
public class EjercicioController {
  private final EjercicioService ejercicioService;

  @GetMapping
  public List<EjercicioRes> listarEjercicios() {
    return ejercicioService.listarEjercicios();
  }

  @PostMapping
  public ResponseEntity<?> guardarEjercicio(@RequestBody @Validated EjercicioReq ejercicio) {
    return new ResponseEntity<>(ejercicioService.saveEjercicio(ejercicio),HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminarEjercicio(@PathVariable Integer id) {
    ejercicioService.eliminarEjercicio(id);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
