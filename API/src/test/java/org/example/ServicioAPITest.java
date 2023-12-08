package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class ServicioAPITest {
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:7000"; // URL base de tu servidor de prueba
    }

    @Test
    public void testEndpointMiembro() {

        String bodyRequest = "{" +
                "    \"idMiembro\": 1," +
                "    \"puntaje\": 10," +
                "    \"incidentes\": [" +
                "        {" +
                "        \"fechaApertura\": \"2023-09-08T10:00:00\"," +
                "        \"fechaCierre\": \"2023-09-08T11:00:00\"," +
                "        \"idCreador\": 1," +
                "        \"idCerrador\": 2," +
                "        \"idPrestacion\": 1" +
                "        }" +
                "    ]" +
                "}";



        // Realizar una solicitud GET al endpoint /miembro y verificar la respuesta
        Response response = given()
                .body(bodyRequest)
                .when()
                .post("/miembro");

        // Afirmaciones para verificar el comportamiento esperado
        response.then()
                .statusCode(200) // Verificar que la respuesta tenga código 200 OK
                .body(is(notNullValue())); // Verificar que el cuerpo de la respuesta no sea nulo

        // Obtener el contenido de la respuesta como texto plano
        String responseBody = response.getBody().asString();

        // Convertir el contenido a un valor double
        double puntaje = Double.parseDouble(responseBody);

        // Verificar el valor del puntaje
        assertEquals(10.5, puntaje, 0.01);
    }

    @Test
    public void testEndpointComunidad() {

        String bodyRequest = "{" +
                "    \"puntajesDeMiembros\": [3.0, 4.0, 2.0, 5.0]" +
                "}";

        // Realizar una solicitud GET al endpoint /comunidades y verificar la respuesta
        Response response = given()
                .body(bodyRequest)
                .when()
                .post("/comunidad");

        // Afirmaciones para verificar el comportamiento esperado
        response.then()
                .statusCode(200) // Verificar que la respuesta tenga código 200 OK
                .body(is(notNullValue())); // Verificar que el cuerpo de la respuesta no sea nulo

        // Obtener el contenido de la respuesta como texto plano
        String responseBody = response.getBody().asString();

        // Convertir el contenido a un valor double
        double puntaje = Double.parseDouble(responseBody);

        // Verificar el valor del puntaje
        assertEquals(3.5, puntaje, 0.01);
    }
}
