package com.example.pruebatecnica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.pruebatecnica.dto.TodoCreateRequest;
import com.example.pruebatecnica.dto.TodoPatchRequest;
import com.example.pruebatecnica.model.Todo;
import com.example.pruebatecnica.services.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoService service;

    @Operation(summary = "Traer todos los ToDos", description = "Trae todos los ToDos en la base de datos")
    @GetMapping()
    public ResponseEntity<?> getAllTodos() {
        return ResponseEntity.ok(Map.of("data", service.getAllTodos(), "success", true));
    }

    @Operation(summary = "Insertar un ToDo")
    @ApiResponse(responseCode = "201", description = "ToDo creado correctamente", content = @Content(examples = @ExampleObject(value = "{\n'success':true, \n'data': {\n'id': 1,\n 'descripcion': 'Hacer mi tarea',\n 'estado':'Pendiente',\n 'fechaLimite':'2025-05-17'\n    }\n}")))
    @ApiResponse(responseCode = "400", description = "Errores de validación", content = @Content(examples = @ExampleObject(value = "{\n  'descripcion': 'Es necesario añadir una descripción.',\n  'estado': 'Es necesario brindarle un estado.',\n  'fechaLimite': 'Es necesario añadir una fecha.',\n  'success': false\n}")))
    @PostMapping()
    public ResponseEntity<?> createTodo(@Valid @RequestBody TodoCreateRequest request) {
        Todo todoGuardado = service.createTodo(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todoGuardado.getId())
                .toUri();
        return ResponseEntity.created(location).body(Map.of("data", todoGuardado, "success", true));
    }

    @Operation(summary = "Actualizar un ToDo por completo")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "Errores de validación", content = @Content(examples = {
            @ExampleObject(value = "{\n 'success': false,\n 'message': 'No se ha enviado el id del ToDo a editar.'\n }", name = "No se envió el ID a editar"), 
            @ExampleObject(value = "{\n  'success':false,\n  'message':'Los IDs no coinciden.'\n}", name = "Los IDs no coiniciden"), 
            @ExampleObject(value = "{\n  'descripcion': 'Es necesario añadir una descripción.',\n  'estado': 'Es necesario brindarle un estado.',\n  'fechaLimite': 'Es necesario añadir una fecha.',\n  'success': false\n}", name = "Errores de validación")})),
        @ApiResponse(responseCode = "200", description = "ToDo actualizado correctamente", content = @Content(examples = @ExampleObject(value = "{\n  'data': {\n    'id': 1,\n    'descripcion': 'Hacer mi tarea',\n    'fechaLimite': '2025-05-17',\n    'estado': 'Completado'\n  },\n  'success': true\n}"))),
        @ApiResponse(responseCode = "404", description = "No se encontró el ToDo a actualizar", content = @Content(examples = @ExampleObject(value = "{\n 'success': false,\n 'message': 'Los IDs no coinciden.'\n }")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> putTodo(@PathVariable Long id, @Valid @RequestBody Todo request) {
        return service.putTodo(id, request);
    }

    @Operation(summary = "Actualizar el estado de un ToDo")
    @ApiResponses({
         @ApiResponse(responseCode = "404", description = "No se encontró el ToDo a actualizar", content = @Content(examples = @ExampleObject(value = "{\n 'success': false,\n 'message': 'Los IDs no coinciden.'\n }"))),
         @ApiResponse(responseCode = "400", description = "Errores de validación", content = @Content(examples = {
            @ExampleObject(value = "{\n 'success': false,\n 'message': 'No se ha enviado el id del ToDo a editar.'\n }", name = "No se envió el ID del ToDo a actualizar"),
            @ExampleObject(value = "{\n 'success': false,\n 'message': 'No se ha enviado la información del estado a editar, no se realizara ningún cambio.'\n }", name = "No se envió el campo del estado a actualizar"),
            @ExampleObject(value = "{\n 'success': false,\n 'message': 'El estado no puede venir vacio.'\n }", name = "El estado enviado viene vacio")
         })),
         @ApiResponse(responseCode = "200", description = "Estado del ToDo actualizado", content = @Content(examples = @ExampleObject(value = "{\n  'data': {\n    'id': 1,\n    'descripcion': 'Hacer mi tarea',\n    'fechaLimite': '2025-05-17',\n    'estado': 'Completado'\n  },\n  'success': true\n}")))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchTodo(@PathVariable Long id, @RequestBody TodoPatchRequest request) {
        return service.patchTodo(id, request);
    }

    @Operation(summary = "Borrar un ToDo")
    @ApiResponses({
        @ApiResponse(responseCode = "404", description = "Intentar borrar un ToDo que no existe", content = @Content(examples = @ExampleObject(value = "{\n    'success':false,\n    'message':'No se encontró el todo con id 3'\n}"))),
        @ApiResponse(responseCode = "200", description = "ToDo borrado exitosamente", content = @Content(examples = @ExampleObject(value = "{\n    'success':true,\n    'message':'El ToDo fue eliminado correctamente.'\n}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        return service.deleteTodo(id);
    }

}
