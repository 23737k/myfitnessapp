package com.myfitnessapp.controllers;

import com.myfitnessapp.dto.request.ItemRutinaReq;
import com.myfitnessapp.dto.response.ItemRutinaRes;
import com.myfitnessapp.services.ItemRutinaService;
import com.myfitnessapp.validation.Crear;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rutinas/{rutinaId}/items")
public class ItemRutinaController {
    private final ItemRutinaService itemRutinaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRutinaRes> getItems(@PathVariable("rutinaId") Integer id){
        return itemRutinaService.getItems(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRutinaRes crearItem(@PathVariable("rutinaId") Integer id, @RequestBody @Validated(Crear.class)
    ItemRutinaReq itemDto){
        return itemRutinaService.agregarItem(id, itemDto);

    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ItemRutinaRes modificarItem(@PathVariable("rutinaId") Integer rutinaId, @PathVariable("id")Integer itemId,
                                           @RequestBody @Validated ItemRutinaReq itemDto){
        return itemRutinaService.modificarItem(itemDto, rutinaId,itemId);
    }


//    @PostMapping("/cambiarOrden")
//    public ResponseEntity<?> cambiarOrdenItem(@PathVariable("rutinaId") Integer id, @RequestBody @Validated
//    CambiarOrdenItemRequest cambiarOrdenItemRequest){
//        rutinaService.cambiarOrdenItem(id, cambiarOrdenItemRequest);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarItem(@PathVariable("itemId") Integer itemId, @PathVariable("rutinaId") Integer rutinaId){
        itemRutinaService.eliminarItem(rutinaId, itemId);
    }
}
