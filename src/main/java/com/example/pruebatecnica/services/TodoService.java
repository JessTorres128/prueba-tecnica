package com.example.pruebatecnica.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.pruebatecnica.dto.TodoCreateRequest;
import com.example.pruebatecnica.dto.TodoPatchRequest;
import com.example.pruebatecnica.model.Todo;
import com.example.pruebatecnica.repository.TodoRepository;

import jakarta.transaction.Transactional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public List<Todo> getAllTodos() {
        return repository.findAll();
    }

    @Transactional
    public Todo createTodo(TodoCreateRequest request) {
        Todo newTodo = new Todo();
        newTodo.setDescripcion(request.getDescripcion());
        newTodo.setEstado(request.getEstado());
        newTodo.setFechaLimite(request.getFechaLimite());
        return repository.save(newTodo);
    }

    @Transactional
    public ResponseEntity<?> putTodo(Long id, Todo requestTodo) {
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "No se ha enviado el id del ToDo a editar."));
        }
        if (!id.equals(requestTodo.getId()) || requestTodo.getId() == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Los IDs no coinciden."));
        }
        Optional<Todo> optTodo = repository.findById(id);
        if (!optTodo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "No se ha encontrado el todo con el id " + id));
        }
        Todo databaseTodo = optTodo.get();

        databaseTodo.setDescripcion(requestTodo.getDescripcion());
        databaseTodo.setEstado(requestTodo.getEstado());
        databaseTodo.setFechaLimite(requestTodo.getFechaLimite());
        Todo newTodo = repository.save(databaseTodo);
        return ResponseEntity.ok(Map.of("success", true, "data", newTodo));
    }
    
    @Transactional
    public ResponseEntity<?> patchTodo(Long id, TodoPatchRequest request) {
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "No se ha enviado el id del ToDo a editar."));
        }
        Optional<Todo> optTodo = repository.findById(id);
        if (!optTodo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "No se ha encontrado el todo con el id " + id));
        }
        Todo databaseTodo = optTodo.get();
        if (request.getEstado() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message",
                            "No se ha enviado la información del estado a editar, no se realizara ningún cambio."));

        }
        if (request.getEstado().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "El estado no puede venir vacio."));
        }
        databaseTodo.setEstado(request.getEstado().trim());

        Todo patchedTodo = repository.save(databaseTodo);
        return ResponseEntity.ok(Map.of("success", true, "data", patchedTodo));
    }
    
    @Transactional
    public ResponseEntity<?> deleteTodo(Long id) {
        Optional<Todo> optTodo = repository.findById(id);
        if (!optTodo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "No se encontró el todo con id " + id));
        }
        repository.delete(optTodo.get());
        return ResponseEntity.ok(Map.of("success", true, "message", "El ToDo fue eliminado correctamente."));
    }
}
