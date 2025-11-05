package guru.nicks.swagger;

import guru.nicks.swagger.config.CommonsSwaggerAutoConfiguration;

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
@SecurityRequirement(name = CommonsSwaggerAutoConfiguration.OPENID_SCHEME_NAME)
@SecurityRequirement(name = CommonsSwaggerAutoConfiguration.BEARER_TOKEN_SCHEME_NAME)
@SecurityRequirement(name = CommonsSwaggerAutoConfiguration.API_KEY_AUTH_SCHEME_NAME)
@SecurityRequirement(name = CommonsSwaggerAutoConfiguration.BASIC_AUTH_SCHEME_NAME)
public @interface OpenApiAuth {
}
