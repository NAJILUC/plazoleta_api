
package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.CreateUserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.dto.response.common.BasicResponse;
import com.pragma.plazoleta.application.handler.IUserHandler;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All users returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BasicResponse.class)
                    )
            )
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<BasicResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(
                BasicResponse.of(userHandler.getAllUsers())
        );
    }

    @Operation(summary = "Get a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BasicResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<BasicResponse<UserResponse>> getUserById(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
                BasicResponse.of(userHandler.getUserById(userId))
        );
    }

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BasicResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user data",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<BasicResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest createUserRequest) {

        UserResponse userResponse = userHandler.saveUser(createUserRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BasicResponse.of(userResponse));
    }

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User updated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody CreateUserRequest createUserRequest) {

        userHandler.updateUser(userId, createUserRequest);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {

        userHandler.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
