package Calculadores;

import JSONs.Comunidad;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.openapi.*;
import org.json.JSONObject;
import java.util.List;

public class CalculadorPuntajeComunidad {

    @OpenApi(
            summary = "Calcular puntaje de una comunidad",
            operationId = "calcularPuntajeComunidad",
            path = "/comunidad",
            methods = HttpMethod.POST,
            tags = {"Comunidad"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = Comunidad.class)}),
            responses = {
                    @OpenApiResponse(status = "200")
            }
    )
    public static void calcularPuntajeComunidad(Context ctx) throws JsonProcessingException {

        /*
            {
                "puntajesDeMiembros": [3.0, 4.0, 2.0, 5.0]
            }
        */


        // Obtenemos el JSON del body
        JSONObject requestBody = new JSONObject(ctx.body());

        // Convierte el objeto JSON a una cadena
        String jsonString = requestBody.toString();

        // Imprime la cadena JSON
        System.out.println("Cadena JSON: " + jsonString);

        // Crear un ObjectMapper para mapear el JSON a un objeto Java
        ObjectMapper objectMapper = new ObjectMapper();
        Comunidad datosComunidad = objectMapper.readValue(jsonString, Comunidad.class);

        double puntosFinales = puntajeComunidad(datosComunidad.getPuntajesDeMiembros());

        // Devolver una respuesta si es necesario
        ctx.result(String.valueOf(puntosFinales));

    }


    public static double puntajeComunidad(List<Double> lista) {
        double promedio = lista.stream()
                .mapToDouble(Double::doubleValue) // Convierte los elementos a tipo double
                .average() // Calcula el promedio
                .orElse(0.0); // Valor predeterminado si la lista está vacía

        for (Double puntaje : lista) {
            if (puntaje < 2.0) {
                promedio -= 0.2;
            }
        }

        return promedio;
    }

}
