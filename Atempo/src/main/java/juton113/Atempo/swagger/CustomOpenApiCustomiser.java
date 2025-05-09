package juton113.Atempo.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;
import org.springdoc.core.customizers.OpenApiCustomizer;

@Component
public class CustomOpenApiCustomiser implements OpenApiCustomizer {
    @Override
    public void customise(OpenAPI openApi) {
        Schema<?> hospitalInfoSchema = new ObjectSchema()
                .addProperty("name", new Schema<>().example("test_name"))
                .addProperty("phoneNumber", new Schema<>().example("+82123-456"))
                .addProperty("address", new Schema<>().example("test_address"))
                .addProperty("distance", new Schema<>().example(2.0))
                .addProperty("travelTime", new Schema<>().example(20))
                .addProperty("departments", new Schema<>().example("department1,department2,department3"));

        openApi.getComponents().addSchemas("HospitalInfoResponse", hospitalInfoSchema);
    }
}
