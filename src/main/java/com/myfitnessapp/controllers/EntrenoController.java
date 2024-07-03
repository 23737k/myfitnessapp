package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.EntrenoRequestDto;
import com.myfitnessapp.dto.response.EntrenoResponseDto;
import com.myfitnessapp.services.EntrenoService;
import com.myfitnessapp.validation.Crear;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/entrenos")
public class EntrenoController {
    private final EntrenoService entrenoService;

    @GetMapping("")
    public ResponseEntity<?> listarEntrenos(){
        List<EntrenoResponseDto> entrenos = entrenoService.getEntrenos().stream().map(entrenoService::toEntrenoResponseDto).toList();
        return new ResponseEntity<>(entrenos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEntrenoPorId(@PathVariable Integer id){
        return new ResponseEntity<>(entrenoService.toEntrenoResponseDto(entrenoService.findById(id)),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> nuevoEntreno(@RequestBody @Validated(Crear.class) EntrenoRequestDto entrenoRequestDto){
        entrenoService.saveEntreno(entrenoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEntreno(@PathVariable Integer id){
        entrenoService.deleteEntreno(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
