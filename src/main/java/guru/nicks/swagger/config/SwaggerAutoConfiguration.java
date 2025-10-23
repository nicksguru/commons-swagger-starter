package guru.nicks.swagger.config;

import guru.nicks.swagger.domain.SwaggerProperties;

import am.ik.yavi.meta.ConstraintArguments;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

import static guru.nicks.validation.dsl.ValiDsl.checkNotNull;

/**
 * OpenAPIv3 config. Creates beans if the official Springfox enabler flag ({@code springdoc.api-docs.enabled}) is on.
 * Beans created here merely add something to Swagger pages: authentication types and API title.
 */
@ConditionalOnProperty(value = "springdoc.api-docs.enabled", havingValue = "true")
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    /**
     * Created dynamically by this class. Should be mentioned in controllers' {@link SecurityRequirement#name()} to make
     * Swagger UI pass JWT to server during each call.
     * <p>
     * WARNING: no whitespaces allowed, otherwise <a href="https://editor.swagger.io">https://editor.swagger.io</a> will
     * complain.
     */
    public static final String OPENID_SCHEME_NAME = "OpenID";
    public static final String BEARER_TOKEN_SCHEME_NAME = "AuthToken";
    public static final String BASIC_AUTH_SCHEME_NAME = "BasicAuth";

    /**
     * This is the header name, actually.
     */
    public static final String API_KEY_AUTH_SCHEME_NAME = "X-API-Key";

    @ConditionalOnMissingBean(OpenAPI.class)
    @Bean
    public OpenAPI openApi(SwaggerProperties swaggerProperties) {
        SpringDocUtils.getConfig()
                // don't list currently logged-in user among controller arguments
                .addRequestWrapperToIgnore(AuthenticatedPrincipal.class)
                .addRequestWrapperToIgnore(Principal.class)
                .addAnnotationsToIgnore(AuthenticationPrincipal.class);

        return new OpenAPI()
                // workaround for correctly detecting HTTPS facade endpoint (without this, it'll be always HTTP)
                .addServersItem(new Server().url("/"))
                .components(new Components()
                        // WARNING: these schemes only work if added to @OpenApiAuth as @SecurityRequirement
                        .addSecuritySchemes(BASIC_AUTH_SCHEME_NAME, createBasicSecurityScheme())
                        .addSecuritySchemes(API_KEY_AUTH_SCHEME_NAME, createApiKeySecurityScheme())
                        .addSecuritySchemes(BEARER_TOKEN_SCHEME_NAME, createBearerSecurityScheme())
                        .addSecuritySchemes(OPENID_SCHEME_NAME, createOauthSecurityScheme(swaggerProperties.getOauth()))
                ).info(createApiInfo(swaggerProperties.getApiInfo()));
    }

    /**
     * See <a href="https://swagger.io/docs/specification/authentication/">this manual</a>.
     */
    @ConstraintArguments
    private SecurityScheme createOauthSecurityScheme(SwaggerProperties.OAuth oauth) {
        checkNotNull(oauth, _SwaggerAutoConfigurationCreateOauthSecuritySchemeArgumentsMeta.OAUTH.name());
        var scopes = new Scopes();

        Optional.ofNullable(oauth.getScopes())
                .orElseGet(TreeSet::new)
                .forEach(value -> scopes.addString(value, value));

        return new SecurityScheme()
                .name(OPENID_SCHEME_NAME)
                .type(SecurityScheme.Type.OAUTH2)
                .in(SecurityScheme.In.HEADER)
                // retrieve id_token instead of access_token (the latter is NOT a JWT in Google)
                .extensions(Map.of("x-tokenName", "id_token"))
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl(oauth.getAuthorizationUrl())
                                .tokenUrl(oauth.getTokenUrl())
                                .refreshUrl(oauth.getRefreshUrl())
                                .scopes(scopes)));
    }

    private SecurityScheme createBearerSecurityScheme() {
        return new SecurityScheme()
                // this magic work tells Swagger to display token input form
                .scheme("bearer")
                .name(BEARER_TOKEN_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP);
    }

    private SecurityScheme createBasicSecurityScheme() {
        return new SecurityScheme()
                // this magic work tells Swagger to display Username and Password input form
                .scheme("basic")
                .name(BASIC_AUTH_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP);
    }

    private SecurityScheme createApiKeySecurityScheme() {
        return new SecurityScheme()
                .name(API_KEY_AUTH_SCHEME_NAME)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER);
    }

    @ConstraintArguments
    private Info createApiInfo(SwaggerProperties.ApiInfo apiInfo) {
        checkNotNull(apiInfo, _SwaggerAutoConfigurationCreateApiInfoArgumentsMeta.APIINFO.name());

        return new Info()
                .title(apiInfo.getTitle())
                .description(apiInfo.getDescription())
                .version(apiInfo.getVersion())
                .termsOfService(apiInfo.getTermsOfServiceUrl())
                .contact(new Contact()
                        .name(apiInfo.getContact().getName())
                        .url(apiInfo.getContact().getUrl())
                        .email(apiInfo.getContact().getEmail()))
                .license(new License()
                        .name(apiInfo.getLicense())
                        .url(apiInfo.getLicenseUrl()));
    }

}
