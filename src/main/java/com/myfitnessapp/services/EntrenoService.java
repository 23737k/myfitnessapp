package com.myfitnessapp.services;

import com.myfitnessapp.dominio.entreno.Entreno;
import com.myfitnessapp.dominio.rutina.ItemRutina;
import com.myfitnessapp.dominio.rutina.Rutina;
import com.myfitnessapp.dto.request.EntrenoRequestDto;
import com.myfitnessapp.dto.response.EntrenoResponseDto;
import com.myfitnessapp.dto.response.ItemRutinaResponseDto;
import com.myfitnessapp.exceptions.InvalidReferenceException;
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

    public void saveEntreno(EntrenoRequestDto entrenoRequestDto){
        entrenoRepo.save(toEntreno(entrenoRequestDto));
    }

    public Entreno toEntreno(EntrenoRequestDto dto){
        Rutina rutina = rutinaRepo.findById(dto.getRutinaId()).orElseThrow(()->new InvalidReferenceException("No existe la rutina"));
        List<ItemRutina> items = dto.getItems() != null? dto.getItems().stream().map(itemRutinaService::toItemRutina).toList():null;
        return new Entreno(rutina,items,dto.getDuracionEnMinutos(),dto.getInicio());
    }

    public EntrenoResponseDto toEntrenoResponseDto(Entreno entreno){
        return EntrenoResponseDto.builder()
                .id(entreno.getId())
                .rutinaId(entreno.getRutina().getId())
                .rutinaNombre(entreno.getRutina().getNombre())
                .inicio(entreno.getInicio())
                .duracionEnMinutos(entreno.getDuracionEnMinutos())
                .volumenEnKg(entreno.getVolumen())
                .nroDeSeries(entreno.getNroDeSeries())
                .items(entreno.getItems().stream().map(itemRutinaService::toItemResponseDto).toList())
                .build();
    }

}
