package guru.nicks.swagger;

import guru.nicks.swagger.config.SwaggerConfig;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Swagger-related annotations to let Swagger UI users authenticate using various schemes.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// without this, Swagger UI won't pass auth header to server
@SecurityRequirement(name = SwaggerConfig.OPENID_SCHEME_NAME)
@SecurityRequirement(name = SwaggerConfig.BEARER_TOKEN_SCHEME_NAME)
@SecurityRequirement(name = SwaggerConfig.API_KEY_AUTH_SCHEME_NAME)
@SecurityRequirement(name = SwaggerConfig.BASIC_AUTH_SCHEME_NAME)
public @interface OpenApiAuth {
}
