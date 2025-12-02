package com.ejemplo.cine.service;

import com.ejemplo.cine.model.Espectador;
import com.ejemplo.cine.repository.EspectadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspectadorService {

    @Autowired
    private EspectadorRepository espectadorRepository;

    public List<Espectador> listarTodos() {
        return espectadorRepository.findAll();
    }

    public Optional<Espectador> obtenerPorId(Long id) {
        return espectadorRepository.findById(id);
    }

    public Espectador guardar(Espectador espectador) {
        return espectadorRepository.save(espectador);
    }

    public Espectador actualizar(Long id, Espectador datosActualizados) {
        Espectador existente = espectadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espectador no encontrado con ID: " + id));

        if (datosActualizados.getNombre() != null) {
            existente.setNombre(datosActualizados.getNombre());
        }
        if (datosActualizados.getDni() != null) {
            existente.setDni(datosActualizados.getDni());
        }

        return espectadorRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!espectadorRepository.existsById(id)) {
            throw new RuntimeException("Espectador no encontrado con ID: " + id);
        }
        espectadorRepository.deleteById(id);
    }
}