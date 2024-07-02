package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.EntrenoRequestDto;
import com.myfitnessapp.dto.response.EntrenoResponseDto;
import com.myfitnessapp.services.EntrenoService;
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

    @PostMapping("")
    public ResponseEntity<?> nuevoEntreno(@RequestBody @Validated EntrenoRequestDto entrenoRequestDto){
        entrenoService.saveEntreno(entrenoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
