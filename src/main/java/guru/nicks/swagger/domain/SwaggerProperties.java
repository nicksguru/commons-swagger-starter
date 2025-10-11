package guru.nicks.swagger.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.jackson.Jacksonized;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.TreeSet;

@ConfigurationProperties(prefix = "swagger")
@Validated
// immutability
@Value
@NonFinal // needed for CGLIB to bind property values (nested classes don't need this)
@Jacksonized
@Builder(toBuilder = true)
public class SwaggerProperties {

    @NotNull
    @Valid // not validated by Spring Boot 3.4 without this
    OAuth oauth;

    @NotNull
    @Valid // not validated by Spring Boot 3.4 without this
    SwaggerProperties.ApiInfo apiInfo;

    @Value
    @Jacksonized
    @Builder(toBuilder = true)
    public static class OAuth {

        @NotBlank
        String authorizationUrl;

        @NotBlank
        String tokenUrl;

        String refreshUrl;

        // sorted for readability
        TreeSet<String> scopes;

    }

    @Value
    @Jacksonized
    @Builder(toBuilder = true)
    public static class ApiInfo {

        String title;
        String description;
        String version;
        String termsOfServiceUrl;
        String license;
        String licenseUrl;

        @NotNull
        Contact contact;

        @Value
        @Jacksonized
        @Builder(toBuilder = true)
        public static class Contact {

            String name;
            String url;
            String email;

        }

    }

}
