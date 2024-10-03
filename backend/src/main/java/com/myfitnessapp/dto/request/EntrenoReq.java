package com.myfitnessapp.dto.request;

import com.myfitnessapp.validation.Crear;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrenoReq {
    @NotNull (message = "El campo rutinaId no debe estar vacío", groups = {Crear.class})
    private Integer rutinaId;
    @NotNull(message = "El campo fecha no debe estar vacío", groups = {Crear.class})
    private LocalDateTime fecha;
    @NotNull(message = "El campo duracionEnSeg no debe estar vacío", groups = {Crear.class})
    private Long duracionEnSeg;
    @NotEmpty(message = "El campo items no debe estar vacío", groups = {Crear.class})
    @Size(min = 1, message = "Debe haber al menos un item", groups = {Crear.class})
    @Valid
    private List<ItemRutinaReq> items;
}
