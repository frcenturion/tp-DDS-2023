import Calculadores.CalculadorPuntajeMiembro;
import Calculadores.CalculadorPuntajeComunidad;

import io.javalin.Javalin;

import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.OpenApiPluginConfiguration;
import io.javalin.openapi.plugin.redoc.ReDocConfiguration;
import io.javalin.openapi.plugin.redoc.ReDocPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;
import static io.javalin.apibuilder.ApiBuilder.*;


public class ServicioAPI {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();
            openApiConfiguration.getInfo().setTitle("API para calcular el puntaje de un miembro o una comunidad");
            config.plugins.register(new OpenApiPlugin(new OpenApiPluginConfiguration()
                    .withDocumentationPath("/openapi")));
            config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));
            config.plugins.register(new ReDocPlugin(new ReDocConfiguration()));

        }).routes(() -> {
            path("miembro", () -> {
                post(CalculadorPuntajeMiembro::calcularPuntajeMiembro);
            });

            path("comunidad", () -> {
                post(CalculadorPuntajeComunidad::calcularPuntajeComunidad);
            });
        }).start(7000);
    }
}