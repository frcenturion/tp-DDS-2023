package JSONs;

import java.util.ArrayList;
import java.util.List;


public class Miembro {
    private Double puntaje;
    private List<Incidente> incidentes;
    private int idMiembro;


    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public List<Incidente> getIncidentes() {
        return incidentes;
    }

    public void setIncidentes(List<Incidente> incidentes) {
        this.incidentes = incidentes;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public void sumarPuntaje(Double puntos) {
        this.puntaje += puntos;
    }

    public List<Incidente> incidentesPropios() {
        List<Incidente> incidentesPropios = new ArrayList<>();

        for (Incidente incidente : incidentes) {
            if (incidente.getIdCreador() == idMiembro || incidente.getIdCerrador() == idMiembro) {
                incidentesPropios.add(incidente);
            }
        }

        return incidentesPropios;
    }
}