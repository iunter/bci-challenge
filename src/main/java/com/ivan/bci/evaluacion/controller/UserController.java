package com.ivan.bci.evaluacion.controller;

import com.ivan.bci.evaluacion.model.User;
import com.ivan.bci.evaluacion.model.UserRequest;
import com.ivan.bci.evaluacion.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController
{

    @Autowired
    private UserService userService;

    @Operation(summary = "Crea un nuevo usuario", description = "Retorna un nuevo usuario")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado con exito"),
            @ApiResponse(responseCode = "400", description = "Hubo un error (mal contraseña, mail o usuario ya existente")
    })
    public ResponseEntity<Object> newUser(
            @RequestBody
            @Parameter(name = "userRequest", description = "Request con el formato descrito en la consigna")
            UserRequest userRequest)
    {
        try
        {
            User user = userService.addUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage(e.getMessage()));
        }
    }

    private String errorMessage(String message)
    {
        return "{\"mensaje\": \"" + message + "\"";
    }

}
