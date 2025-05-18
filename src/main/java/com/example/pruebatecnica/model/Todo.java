package com.example.pruebatecnica.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
