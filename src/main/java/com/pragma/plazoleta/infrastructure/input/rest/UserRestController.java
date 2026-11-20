package com.pragma.plazoleta.infrastructure.input.rest;

import com.pragma.plazoleta.application.dto.request.CreateUserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.application.handler.UserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserRestController {

    private final UserHandler userHandler;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userHandler.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userHandler.getUserById(userId));
    }

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        userHandler.saveUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody @Valid CreateUserRequest createUserRequest) {
        userHandler.updateUser(userId, createUserRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userHandler.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}
