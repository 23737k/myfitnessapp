package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.EntrenoRequestDto;
import com.myfitnessapp.services.EntrenoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/entrenos")
public class EntrenoController {
    private final EntrenoService entrenoService;

    @GetMapping("")
    public ResponseEntity<?> listarEntrenos(){
        return new ResponseEntity<>(entrenoService.getEntrenos(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> nuevoEntreno(@RequestBody @Validated EntrenoRequestDto entrenoRequestDto){
        entrenoService.saveEntreno(entrenoRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
