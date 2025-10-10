package guru.nicks.swagger;

import guru.nicks.rest.v1.dto.BusinessExceptionDto;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * REST API response statuses augmented with {@link BusinessExceptionDto} when applicable.
 */
public interface OpenApiResponse {

    /**
     * 200
     */
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(responseCode = "200", description = "Success")
    @interface Ok {
    }

    /**
     * 201 - returned after creating something.
     */
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(responseCode = "201", description = "Resource created")
    @interface Created {
    }

    /**
     * 204 - returned after deleting something.
     */
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(responseCode = "204", description = "Resource deleted",
            content = @Content(schema = @Schema(implementation = Void.class)))
    @interface NoContent {
    }

    /**
     * 206 (Partial Content) -  for returning paginated results.
     */
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(responseCode = "206", description = "Paginated content")
    @interface PartialContent {
    }

    /**
     * 304 (Not Modified) - for cases when ETag passed by caller matches the one on server side.
     */
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(responseCode = "304", description = "Server has the same resource version as client's ETag header",
            content = @Content(schema = @Schema(implementation = Void.class)))
    @interface EtagMatched {
    }

    /**
     * 404
     */
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(responseCode = "404", description = "Resource not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BusinessExceptionDto.class)))
    @interface NotFound {
    }

    /**
     * 409
     */
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(responseCode = "409", description = "Operation not allowed in current resource state",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BusinessExceptionDto.class)))
    @interface Conflict {
    }

}
