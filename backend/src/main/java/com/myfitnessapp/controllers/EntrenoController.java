package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.EntrenoReq;
import com.myfitnessapp.dto.response.EntrenoRes;
import com.myfitnessapp.services.EntrenoService;
import com.myfitnessapp.validation.Crear;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/entrenos")
public class EntrenoController {
    private final EntrenoService entrenoService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<EntrenoRes> listarEntrenos(){
        return entrenoService.getEntrenos().stream().map(entrenoService::toEntrenoResponseDto).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntrenoRes obtenerEntrenoPorId(@PathVariable Integer id){
        return entrenoService.toEntrenoResponseDto(entrenoService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntrenoRes nuevoEntreno(@RequestBody @Validated(Crear.class)
                                          EntrenoReq entrenoReq){
        return entrenoService.toEntrenoResponseDto(entrenoService.saveEntreno(entrenoReq));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEntreno(@PathVariable Integer id){
        entrenoService.deleteEntreno(id);
    }
}
