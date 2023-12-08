package com.stack.stack.clases;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import com.stack.stack.clases.Comunidad;
import com.stack.stack.clases.Miembro;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "estado_por_comunidad")
public class EstadoPorComunidad {
    @Id
    @GeneratedValue
    @Column(name = "id_estado_por_comunidad", nullable = true)
    private Long idEstadoPorComunidad;

    @ManyToOne
    @JoinColumn(name = "id_comunidad")
    private Comunidad comunidad;

    @ManyToOne
    @JoinColumn(name = "id_incidente")
    private Incidente incidente;

    @Column(name = "esta_abierto")
    public Boolean estaAbierto;

    @Column(name = "fecha_de_cierre")
    private LocalDateTime fechaDeCierre;

    @ManyToOne
    @JoinColumn(name = "id_miembro_cerrador")
    private Miembro cerrador;




    public EstadoPorComunidad(){}

    public void setEstaAbierto(boolean nuevoEstado) {
        this.estaAbierto = nuevoEstado;
    }

    public Comunidad getComunidad() {
        return this.comunidad;
    }

    public Incidente getIncidente() {
        return this.incidente;
    }

    public Long getIdEstadoPorComunidad() {
        return this.idEstadoPorComunidad;
    }

    public Boolean getEstaAbierto() {
        return this.estaAbierto;
    }

    public LocalDateTime getFechaDeCierre(){
        return this.fechaDeCierre;
    }

    public Miembro getCerrador() {
        return this.cerrador;
    }

    public void setCerrador(Miembro cerrador) {
        this.cerrador = cerrador;
    }

}

