package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.CreateRestaurantRequest;
import com.pragma.plazoleta.application.dto.request.CreateUserRequest;
import com.pragma.plazoleta.application.dto.response.RestaurantResponse;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.dto.response.common.BasicResponse;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/restaurants")
@PreAuthorize("hasRole('ADMINISTRADOR')")
@RequiredArgsConstructor
public class RestaurantRestController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = "Create a restaurant")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Restaurant created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BasicResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid restaurant data",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<BasicResponse<RestaurantResponse>> createRestaurant(
            @Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {

        RestaurantResponse  restaurantResponse = restaurantHandler.saveRestaurant(createRestaurantRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BasicResponse.of(restaurantResponse));
    }
}
