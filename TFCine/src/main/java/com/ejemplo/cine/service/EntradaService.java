package com.ejemplo.cine.service;

import com.ejemplo.cine.model.Entrada;
import com.ejemplo.cine.model.Espectador;
import com.ejemplo.cine.model.Pelicula;
import com.ejemplo.cine.repository.EntradaRepository;
import com.ejemplo.cine.repository.EspectadorRepository;
import com.ejemplo.cine.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaService {

    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private EspectadorRepository espectadorRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<Entrada> listarTodas() {
        return entradaRepository.findAll();
    }

    public Optional<Entrada> obtenerPorId(Long id) {
        return entradaRepository.findById(id);
    }

    public Entrada crearEntrada(Entrada entrada) {
        // Validar que las relaciones existan
        if (entrada.getEspectador() == null || entrada.getEspectador().getId() == null) {
            throw new RuntimeException("El espectador es obligatorio");
        }
        if (entrada.getPelicula() == null || entrada.getPelicula().getId() == null) {
            throw new RuntimeException("La película es obligatoria");
        }

        Espectador espectador = espectadorRepository.findById(entrada.getEspectador().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Espectador no encontrado con ID: " + entrada.getEspectador().getId()));

        Pelicula pelicula = peliculaRepository.findById(entrada.getPelicula().getId())
                .orElseThrow(
                        () -> new RuntimeException("Película no encontrada con ID: " + entrada.getPelicula().getId()));

        // ✅ VALIDAR ASIENTO DISPONIBLE
        validarAsientoDisponible(
                entrada.getNumeroAsiento(),
                entrada.getPelicula().getId(),
                entrada.getFechaHoraFuncion(),
                null);

        Entrada nuevaEntrada = new Entrada();
        nuevaEntrada.setNumeroAsiento(entrada.getNumeroAsiento());
        nuevaEntrada.setFechaHoraFuncion(entrada.getFechaHoraFuncion());
        nuevaEntrada.setEspectador(espectador);
        nuevaEntrada.setPelicula(pelicula);

        return entradaRepository.save(nuevaEntrada);
    }

    public Entrada actualizarEntrada(Long id, Entrada datosActualizados) {
        Entrada existente = entradaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada con ID: " + id));

        if (datosActualizados.getEspectador() == null || datosActualizados.getEspectador().getId() == null) {
            throw new RuntimeException("El espectador es obligatorio");
        }
        Espectador espectador = espectadorRepository.findById(datosActualizados.getEspectador().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Espectador no encontrado con ID: " + datosActualizados.getEspectador().getId()));

        if (datosActualizados.getPelicula() == null || datosActualizados.getPelicula().getId() == null) {
            throw new RuntimeException("La película es obligatoria");
        }
        Pelicula pelicula = peliculaRepository.findById(datosActualizados.getPelicula().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Película no encontrada con ID: " + datosActualizados.getPelicula().getId()));

        // ✅ VALIDAR ASIENTO DISPONIBLE (excluyendo la entrada actual)
        validarAsientoDisponible(
                datosActualizados.getNumeroAsiento(),
                datosActualizados.getPelicula().getId(),
                datosActualizados.getFechaHoraFuncion(),
                id);

        existente.setNumeroAsiento(datosActualizados.getNumeroAsiento());
        existente.setFechaHoraFuncion(datosActualizados.getFechaHoraFuncion());
        existente.setEspectador(espectador);
        existente.setPelicula(pelicula);

        return entradaRepository.save(existente);
    }

    public void eliminarEntrada(Long id) {
        if (!entradaRepository.existsById(id)) {
            throw new RuntimeException("Entrada no encontrada con ID: " + id);
        }
        entradaRepository.deleteById(id);
    }

    /**
     * Valida que un asiento esté disponible para una función específica.
     * 
     * @param numeroAsiento    Número de asiento (ej: "F12")
     * @param peliculaId       ID de la película
     * @param fechaHoraFuncion Fecha y hora de la función (formato: "yyyy-MM-dd
     *                         HH:mm:ss")
     * @param entradaIdExcluir ID de entrada a excluir (para actualizaciones, null
     *                         para creaciones)
     * @throws RuntimeException si el asiento ya está ocupado
     */
    private void validarAsientoDisponible(String numeroAsiento, Long peliculaId, String fechaHoraFuncion,
            Long entradaIdExcluir) {
        boolean existeDuplicado;

        if (entradaIdExcluir != null) {
            existeDuplicado = entradaRepository.existsByNumeroAsientoAndPeliculaIdAndFechaHoraFuncionAndIdNot(
                    numeroAsiento, peliculaId, fechaHoraFuncion, entradaIdExcluir);
        } else {
            existeDuplicado = entradaRepository.existsByNumeroAsientoAndPeliculaIdAndFechaHoraFuncion(
                    numeroAsiento, peliculaId, fechaHoraFuncion);
        }

        if (existeDuplicado) {
            throw new RuntimeException("El asiento '" + numeroAsiento + "' ya está ocupado para esta función.");
        }
    }
}