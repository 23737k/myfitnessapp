package com.myfitnessapp.dto.request;

import com.myfitnessapp.validation.Crear;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenoReq {
    @NotNull (message = "El campo rutinaId no debe estar vacío", groups = {Crear.class})
    private Integer rutinaId;
    @NotNull(message = "El campo inicio no debe estar vacío", groups = {Crear.class})
    private LocalDateTime inicio;
    @NotNull(message = "El campo duracionEnMinutos no debe estar vacío", groups = {Crear.class})
    private Integer duracionEnMinutos;
    @NotEmpty(message = "El campo items no debe estar vacío", groups = {Crear.class})
    @Size(min = 1, message = "Debe haber al menos un item", groups = {Crear.class})
    @Valid
    private List<ItemRutinaReq> items;
}
