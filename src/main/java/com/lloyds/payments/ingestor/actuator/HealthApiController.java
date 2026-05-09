package com.lloyds.payments.ingestor.actuator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the Actuator Health information via a regular MVC endpoint so it appears in Swagger UI.
 * Maps to /api/health to avoid conflicting with the actuator's own /actuator/health path.
 */
@RestController
@Tag(name = "Health", description = "Application health endpoints")
public class HealthApiController {

    private final HealthEndpoint healthEndpoint;

    public HealthApiController(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @Operation(summary = "Get application health", description = "Returns aggregated health information from Spring Boot Actuator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Health status (UP)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "503", description = "Health status (DOWN)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class)))
    })
    @GetMapping("/api/health")
    public ResponseEntity<?> health() {
        // Delegate to actuators HealthEndpoint so the same health information is returned
        return ResponseEntity.ok(this.healthEndpoint.health());
    }
}

