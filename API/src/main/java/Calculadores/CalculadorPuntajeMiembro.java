package Calculadores;
import JSONs.Comunidad;
import JSONs.Miembro;
import JSONs.Incidente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import io.javalin.openapi.*;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


public class CalculadorPuntajeMiembro {

    @OpenApi(
            summary = "Calcular puntaje de un miembro",
            operationId = "calcularPuntajeMiembro",
            path = "/miembro",
            methods = HttpMethod.POST,
            tags = {"Miembro"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = Miembro.class)}),
            responses = {
                    @OpenApiResponse(status = "200")
            }
    )
    public static void calcularPuntajeMiembro(Context ctx) throws JsonProcessingException {

        /*
            {
                "idMiembro": 1,
                "puntaje": 10,
                "incidentes": [
                    {
                    "fechaApertura": "2023-09-08T10:00:00",
                    "fechaCierre": "2023-09-08T11:00:00",
                    "idCreador": 1,
                    "idCerrador": 2,
                    "idPrestacion": 1
                    }
                ]
            }
        */

        // Obtenemos el JSON del body
        System.out.println(ctx.body());

        JSONObject requestBody = new JSONObject(ctx.body());

        // Convierte el objeto JSON a una cadena
        String jsonString = requestBody.toString();

        // Imprime la cadena JSON
        System.out.println("Cadena JSON: " + jsonString);

        // Crear un ObjectMapper para mapear el JSON a un objeto Java
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        Miembro datosMiembro = objectMapper.readValue(jsonString, Miembro.class);

        // Realizar las operaciones necesarias con los datos recibidos
        double puntosFinales = puntajeMiembro(datosMiembro);

        // Devolver una respuesta si es necesario
        ctx.result(String.valueOf(puntosFinales));

    }

    private static double puntajeMiembro(Miembro miembro) {
        // Recorrer el historial de incidentes y aplicar las reglas de cálculo
        for (Incidente incidentePropio : miembro.incidentesPropios()) {
            if(incidentePropio.getIdCerrador() == miembro.getIdMiembro() && (
                    aperturaFraudulenta(incidentePropio) || cierreFraudulento(incidentePropio, miembro)
            )) {
                miembro.sumarPuntaje(-0.2);
            }
            else {
                // Sumar 0.5 puntos si el incidente no es fraudulento
                miembro.sumarPuntaje(0.5);
            }
        }

        return miembro.getPuntaje();
    }

    private static boolean aperturaFraudulenta(Incidente incidentePropio) {
        return tienenPocaDiferencia(incidentePropio, incidentePropio);
    }

    private static boolean cierreFraudulento(Incidente incidentePropio, Miembro miembro) {
        List<Incidente> incidentesSimilares = incidentesSimilares(incidentePropio, miembro.getIncidentes());

        return incidentesSimilares.stream().anyMatch(i -> tienenPocaDiferencia(i, incidentePropio));
    }

    public static List<Incidente> incidentesSimilares(Incidente incidente, List<Incidente> incidentes) {
        // Filtrar los incidentes con el mismo idPrestacion y excluir el incidente pasado como argumento
        return incidentes.stream()
                .filter(i -> i.getIdPrestacion() == incidente.getIdPrestacion() && !i.equals(incidente))
                .collect(Collectors.toList());
    }

    public static boolean tienenPocaDiferencia(Incidente unIncidente, Incidente otroIncidente) {
        LocalDateTime fechaApertura = unIncidente.getFechaApertura();
        LocalDateTime fechaCierre = otroIncidente.getFechaCierre();

        // Calcular la diferencia de tiempo en minutos entre la apertura y el cierre del incidente
        long minutosDiferencia = ChronoUnit.MINUTES.between(fechaApertura, fechaCierre);

        return minutosDiferencia < 3;
    }

}
