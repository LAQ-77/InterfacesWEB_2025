package com.ejemplo.cine.controller;

import com.ejemplo.cine.model.Entrada;
import com.ejemplo.cine.service.EntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    @Autowired
    private EntradaService entradaService;

    @GetMapping
    public ResponseEntity<List<Entrada>> listar() {
        return ResponseEntity.ok(entradaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrada> obtener(@PathVariable("id") Long id) {
        return entradaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Entrada> crear(@RequestBody Entrada entrada) {
        return ResponseEntity.ok(entradaService.crearEntrada(entrada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrada> actualizar(@PathVariable("id") Long id, @RequestBody Entrada datos) {
        try {
            Entrada actualizada = entradaService.actualizarEntrada(id, datos);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        try {
            entradaService.eliminarEntrada(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}