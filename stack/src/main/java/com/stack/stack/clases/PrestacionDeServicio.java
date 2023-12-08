package com.stack.stack.clases;

import com.stack.stack.clases.Miembro;
import com.stack.stack.clases.Incidente;
import java.time.Duration;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "prestacionDeServicio")
public class PrestacionDeServicio {
    @Id
    @GeneratedValue
    private Long idPrestacion;

    //@ManyToOne
    //@JoinColumn(name = "idServicio")
    //private Servicio servicio;

    @ManyToMany
    @JoinTable(
        name = "miembroInteresadoPorPrestacion",
        joinColumns = @JoinColumn(name = "idPrestacion"),
        inverseJoinColumns = @JoinColumn(name = "idMiembro")
    )
    private List<Miembro> miembrosInteresados;

    @OneToMany(mappedBy = "prestacionAfectada")
    private List<Incidente> incidentes;

    @ManyToMany
    @JoinTable(
        name = "prestacion_miembro_relacion",
        joinColumns = @JoinColumn(name = "idPrestacion")
    )
    private List<Miembro> miembrosAfectados;

    //@ManyToOne
    //@JoinColumn(name = "idEstablecimiento", referencedColumnName = "idEstablecimiento")
    //private Establecimiento establecimiento;

    public PrestacionDeServicio() {}


    private boolean pasaronMenosDe24h(LocalDateTime unTiempoInicial, LocalDateTime otroTiempoInicial) {
        return Duration.between(unTiempoInicial, otroTiempoInicial).toHours() < 24;
    }

    public void agregarMiembroInteresado(Miembro miembro) {
        this.miembrosInteresados.add(miembro);
    }

    public void eliminarMiembroInteresado(Miembro miembro) {
        this.miembrosInteresados.remove(miembro);
    }
}
