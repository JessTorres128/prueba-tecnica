package com.example.pruebatecnica.repository;

import org.springframework.stereotype.Repository;

import com.example.pruebatecnica.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    
}
