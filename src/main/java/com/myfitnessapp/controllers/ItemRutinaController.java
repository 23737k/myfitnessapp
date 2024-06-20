package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.CambiarOrdenItemRequest;
import com.myfitnessapp.dto.request.ItemRutinaRequestDto;
import com.myfitnessapp.services.ItemRutinaService;
import com.myfitnessapp.services.RutinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rutinas/{rutinaId}/items")
public class ItemRutinaController {
    private final ItemRutinaService itemRutinaService;
    private final RutinaService rutinaService;
    @GetMapping
    public ResponseEntity<?> getItems(@PathVariable("rutinaId") Integer id){
        return ResponseEntity.ok().body(itemRutinaService.getItems(id));
    }
    @PostMapping
    public ResponseEntity<?> crearItem(@PathVariable("rutinaId") Integer id, @RequestBody ItemRutinaRequestDto itemDto){
        rutinaService.agregarItem(id, itemRutinaService.toItemRutina(itemDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/cambiarOrden")
    public ResponseEntity<?> cambiarOrdenItem(@PathVariable("rutinaId") Integer id, @RequestBody
    CambiarOrdenItemRequest cambiarOrdenItemRequest){
        rutinaService.cambiarOrdenItem(id, cambiarOrdenItemRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> eliminarItem(@PathVariable("itemId") Integer itemId, @PathVariable("rutinaId") Integer rutinaId){
        rutinaService.eliminarItem(rutinaId, itemId);
        return ResponseEntity.ok().build();
    }
}
