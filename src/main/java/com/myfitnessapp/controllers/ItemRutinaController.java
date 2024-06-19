package com.myfitnessapp.controllers;

import com.myfitnessapp.services.ItemRutinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rutinas/{rutinaId}/items")
public class ItemRutinaController {
    private final ItemRutinaService itemRutinaService;

    @GetMapping
    public ResponseEntity<?> getItems(@PathVariable("rutinaId") Integer id){
        return ResponseEntity.ok().body(itemRutinaService.getItems(id));
    }

}
