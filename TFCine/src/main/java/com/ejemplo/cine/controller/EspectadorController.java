package com.ejemplo.cine.controller;

import com.ejemplo.cine.model.Espectador;
import com.ejemplo.cine.service.EspectadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/espectadores")
public class EspectadorController {

    @Autowired
    private EspectadorService espectadorService;

    @GetMapping
    public ResponseEntity<List<Espectador>> listar() {
        return ResponseEntity.ok(espectadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Espectador> obtener(@PathVariable("id") Long id) {
        return espectadorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Espectador> crear(@RequestBody Espectador espectador) {
        return ResponseEntity.ok(espectadorService.guardar(espectador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Espectador> actualizar(@PathVariable("id") Long id, @RequestBody Espectador datos) {
        try {
            Espectador actualizado = espectadorService.actualizar(id, datos);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        try {
            espectadorService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}