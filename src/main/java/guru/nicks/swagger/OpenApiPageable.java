package guru.nicks.swagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Pageable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this to a method which accepts {@link Pageable} to explain each property. Then, annotate the {@link Pageable}
 * argument with {@code @Parameter(hidden = true} to hide it from Swagger.
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
        @Parameter(
                name = "size",
                schema = @Schema(implementation = Integer.class),
                in = ParameterIn.QUERY,
                description = "Maximum number of results on page. If unspecified, default page size applies. "
                        + "If fewer than 'size' items are returned, it doesn't mean there are no more items; "
                        + "the availability of additional pages is indicated by the 'last' flag in the response."),
        @Parameter(
                name = "page",
                schema = @Schema(implementation = Integer.class, example = "0"),
                in = ParameterIn.QUERY,
                description = "Page index to fetch - starts with 0. If unspecified, page 0 is returned."),

        // WARNING: don't put any 'example' here, as if it's a non-existent field, search fails with HTTP status 400
        @Parameter(
                name = "sort",
                in = ParameterIn.QUERY,
                description = "Sorting criteria in the format: 'property' / 'property,asc' / 'property,desc'. "
                        + "If unspecified, no or default sort order is applied, depending on the implementation. "
                        + "For full text search, this parameter is ignored - results are always sorted by relevance. "
                        + "Passing a non-existent property name results in HTTP status 400."),
})
public @interface OpenApiPageable {
}
