package dev.lukmann;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "Authentication", description = "Operations related to authentication"),
                @Tag(name = "Users Management", description = "Operations related to user management"),
        },
        info = @Info(
                title = "Dompetly API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Dompetly API Support",
                        url = "https://dompetly.lukmann.dev",
                        email = "lukmann.dev@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class DompetlyApp extends Application {
}
