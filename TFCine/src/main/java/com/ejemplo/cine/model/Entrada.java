package com.ejemplo.cine.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "entradas", uniqueConstraints = {
        @UniqueConstraint(name = "uk_asiento_funcion", columnNames = { "numero_asiento", "pelicula_id",
                "fecha_hora_funcion" })
})
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroAsiento;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String fechaHoraFuncion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "espectador_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "entradas" })
    private Espectador espectador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pelicula_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "entradas" })
    private Pelicula pelicula;

    public Entrada() {
    }

    public Entrada(String numeroAsiento, String fechaHoraFuncion, Espectador espectador, Pelicula pelicula) {
        this.numeroAsiento = numeroAsiento;
        this.fechaHoraFuncion = fechaHoraFuncion;
        this.espectador = espectador;
        this.pelicula = pelicula;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public String getFechaHoraFuncion() {
        return fechaHoraFuncion;
    }

    public void setFechaHoraFuncion(String fechaHoraFuncion) {
        this.fechaHoraFuncion = fechaHoraFuncion;
    }

    public Espectador getEspectador() {
        return espectador;
    }

    public void setEspectador(Espectador espectador) {
        this.espectador = espectador;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
}