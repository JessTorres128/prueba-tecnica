package com.example.pruebatecnica.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoPatchRequest {
    @Schema(examples = {"Pendiente", "En Progreso", "Completado"}, description = "Estado en el que se encuentra el ToDO")
    private String estado;
}
