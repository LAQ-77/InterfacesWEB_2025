package com.ejemplo.cine.repository;

import com.ejemplo.cine.model.Espectador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspectadorRepository extends JpaRepository<Espectador, Long> {}