package com.stack.stack.clases;

import com.stack.stack.clases.Incidente;
import com.stack.stack.clases.EstadoPorComunidad;


public class IncidenteConEstado {
    public Incidente incidente;
    public EstadoPorComunidad estado;

    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }

    public void setEstado(EstadoPorComunidad unEstado) {
        this.estado = unEstado;
    }

    public EstadoPorComunidad getEstado(){
        return this.estado;
    }

    public Incidente getIncidente() {
        return this.incidente;
    }
}
