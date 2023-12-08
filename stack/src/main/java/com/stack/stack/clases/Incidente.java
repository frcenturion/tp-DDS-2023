package com.stack.stack.clases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "incidente")

public class Incidente {
    @Id
    @GeneratedValue
    @Column(name = "idincidente", nullable = true)
    private Long idIncidente;

    @Column(name = "descripcion")
    public String descripcion;

    @Column(name = "fechaAlta", columnDefinition = "DATE")
    public LocalDateTime tiempoInicial;

    @OneToMany(mappedBy = "incidente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<EstadoPorComunidad> estados;

    @Column(name = "observaciones")
    public String observaciones;

    @ManyToOne
    @JoinColumn(name = "idPrestacion")
    public PrestacionDeServicio prestacionAfectada;

    @ManyToOne
    @JoinColumn(name = "idMiembro", referencedColumnName = "idMiembro")
    public Miembro miembroCreador;

    public Incidente(String observaciones, PrestacionDeServicio prestacion) {
        this.tiempoInicial = LocalDateTime.now();
        this.observaciones = observaciones;
        this.estados = new ArrayList<EstadoPorComunidad>();
        this.prestacionAfectada = prestacion;

    }


    public Incidente() {}



    public EstadoPorComunidad estadoDeComunidad(Integer id_comunidad){
        EstadoPorComunidad estadoBuscado = null;

        for (EstadoPorComunidad estado : this.estados) {
            if (estado.getComunidad().getId() == id_comunidad) {
                estadoBuscado = estado;
                break; // Terminar el bucle una vez que se encuentra el estado
            }
        }

        return estadoBuscado;
    }

    public void agregarEstado(EstadoPorComunidad estadoPorComunidad) {
        this.estados.add(estadoPorComunidad);
    }

    public PrestacionDeServicio getPrestacionAfectada() {
        return this.prestacionAfectada;
    }

    public Long getIdIncidente() {
        return this.idIncidente;
    }

    public List<EstadoPorComunidad> getEstados() {
        return this.estados;
    }

    public LocalDateTime getTiempoInicial() {
        return tiempoInicial;
    }
}
