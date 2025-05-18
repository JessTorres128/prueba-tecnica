package com.example.pruebatecnica.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoCreateRequest {
    @Schema(example = "Hacer mi tarea", description = "Descripción del ToDo")
    @NotBlank(message = "Es necesario añadir una descripción.")
    private String descripcion;

    @Schema(example = "2025-05-17", description = "Fecha límite para el ToDO")
    @FutureOrPresent(message = "No se puede programar una tarea para una fecha que ya pasó.")
    @NotNull(message = "Es necesario añadir una fecha.")
    @Column(name = "fecha_limite")
    private LocalDate fechaLimite;

    @Schema(examples = {"Pendiente", "En Progreso", "Completado"}, description = "Estado en el que se encuentra el ToDO")
    @NotBlank(message = "Es necesario brindarle un estado.")
    private String estado;
}
