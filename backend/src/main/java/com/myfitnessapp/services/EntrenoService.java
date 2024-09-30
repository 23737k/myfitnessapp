package com.myfitnessapp.services;

import com.myfitnessapp.dto.request.EntrenoReq;
import com.myfitnessapp.dto.response.EntrenoRes;
import com.myfitnessapp.exceptions.InvalidReferenceException;
import com.myfitnessapp.model.entreno.Entreno;
import com.myfitnessapp.model.rutina.ItemRutina;
import com.myfitnessapp.model.rutina.Rutina;
import com.myfitnessapp.repositories.EntrenoRepo;
import com.myfitnessapp.repositories.RutinaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntrenoService {
    private final EntrenoRepo entrenoRepo;
    private final RutinaRepo rutinaRepo;
    private final ItemRutinaService itemRutinaService;

    public List<Entreno> getEntrenos(){
        return entrenoRepo.findAll();
    }

    public void saveEntreno(EntrenoReq entrenoReq){
        entrenoRepo.save(toEntreno(entrenoReq));
    }

    public Entreno toEntreno(EntrenoReq dto){
        Rutina rutina = rutinaRepo.findById(dto.getRutinaId()).orElseThrow(()->new InvalidReferenceException("No existe la rutina"));
        List<ItemRutina> items = dto.getItems() != null? dto.getItems().stream().map(itemRutinaService::toItemRutina).toList():null;
        return new Entreno(rutina,items,dto.getDuracionEnMinutos(),dto.getInicio());
    }

    public EntrenoRes toEntrenoResponseDto(Entreno entreno){
        return EntrenoRes.builder()
                .id(entreno.getId())
                .rutinaId(entreno.getRutina().getId())
                .rutinaNombre(entreno.getRutina().getNombre())
                .inicio(entreno.getInicio())
                .duracionEnMinutos(entreno.getDuracionEnMinutos())
                .volumenEnKg(entreno.getVolumen())
                .nroDeSeries(entreno.getNroDeSeries())
                .items(entreno.getItems().stream().map(itemRutinaService::toItemRutinaRes).toList())
                .build();
    }

    public void deleteEntreno(Integer id){
        entrenoRepo.findById(id).orElseThrow(()->new InvalidReferenceException("No existe el entreno"));
        entrenoRepo.deleteById(id);
    }

    public Entreno findById(Integer id){
        return entrenoRepo.findById(id).orElseThrow(()-> new InvalidReferenceException("No existe el entreno"));
    }

}