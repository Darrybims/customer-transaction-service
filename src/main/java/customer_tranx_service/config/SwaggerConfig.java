package customer_tranx_service.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP,
        name = "basicAuth",
        scheme = "basic")
public class SwaggerConfig {
    @Value("${dev.url}")
    private String devUrl;
    @Value("${prod.url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("stephen@gmail.com");
        contact.setName("Stephen");
        contact.setUrl("https://www.bezkoder.com");

        License mitLicense = new License().name("TEST LICENSE").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("TRANSACTION SERVICE API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints for transaction service.").termsOfService("https://www.bezkoder.com/terms")
                .license(mitLicense);


        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .components(new Components()
                        .addSecuritySchemes("basicAuth",createSecurityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }

    private io.swagger.v3.oas.models.security.SecurityScheme createSecurityScheme() {

        return new io.swagger.v3.oas.models.security.SecurityScheme()
                .name("basicAuth")
                .scheme("basic")
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP);
    }
}
