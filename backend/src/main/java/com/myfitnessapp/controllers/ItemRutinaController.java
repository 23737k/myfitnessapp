package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.CambiarOrdenItemRequest;
import com.myfitnessapp.dto.request.ItemRutinaReq;
import com.myfitnessapp.dto.response.ItemRutinaRes;
import com.myfitnessapp.services.ItemRutinaService;
import com.myfitnessapp.services.RutinaService;
import com.myfitnessapp.validation.Crear;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rutinas/{rutinaId}/items")
public class ItemRutinaController {
    private final ItemRutinaService itemRutinaService;
    private final RutinaService rutinaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRutinaRes> getItems(@PathVariable("rutinaId") Integer id){
        return itemRutinaService.getItems(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRutinaRes crearItem(@PathVariable("rutinaId") Integer id, @RequestBody @Validated(Crear.class)
    ItemRutinaReq itemDto){
        return rutinaService.agregarItem(id, itemRutinaService.toItemRutina(itemDto));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> modificarItem(@PathVariable("rutinaId") Integer rutinaId, @PathVariable("id")Integer itemId,
                                           @RequestBody @Validated ItemRutinaReq itemDto){
        rutinaService.modificarItem(itemDto, rutinaId,itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/cambiarOrden")
    public ResponseEntity<?> cambiarOrdenItem(@PathVariable("rutinaId") Integer id, @RequestBody @Validated
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
