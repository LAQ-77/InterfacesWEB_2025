package com.ejemplo.cine.repository;

import com.ejemplo.cine.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    /**
     * Verifica si existe una entrada con el mismo asiento, película y fecha/hora.
     */
    boolean existsByNumeroAsientoAndPeliculaIdAndFechaHoraFuncion(
            String numeroAsiento, Long peliculaId, String fechaHoraFuncion);

    /**
     * Verifica si existe una entrada duplicada, excluyendo una entrada específica
     * (para actualizaciones).
     */
    boolean existsByNumeroAsientoAndPeliculaIdAndFechaHoraFuncionAndIdNot(
            String numeroAsiento, Long peliculaId, String fechaHoraFuncion, Long idExcluir);
}