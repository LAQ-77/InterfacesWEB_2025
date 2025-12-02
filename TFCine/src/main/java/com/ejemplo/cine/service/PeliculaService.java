package com.ejemplo.cine.service;

import com.ejemplo.cine.model.Pelicula;
import com.ejemplo.cine.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<Pelicula> listarTodas() {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> obtenerPorId(Long id) {
        return peliculaRepository.findById(id);
    }

    public Pelicula guardar(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public Pelicula actualizar(Long id, Pelicula datosActualizados) {
        Pelicula existente = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada con ID: " + id));

        if (datosActualizados.getTitulo() != null) {
            existente.setTitulo(datosActualizados.getTitulo());
        }
        if (datosActualizados.getDirector() != null) {
            existente.setDirector(datosActualizados.getDirector());
        }
        if (datosActualizados.getDuracion() != null) {
            existente.setDuracion(datosActualizados.getDuracion());
        }

        return peliculaRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new RuntimeException("Película no encontrada con ID: " + id);
        }
        peliculaRepository.deleteById(id);
    }
}