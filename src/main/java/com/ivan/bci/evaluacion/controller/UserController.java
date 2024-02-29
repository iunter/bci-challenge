package com.ivan.bci.evaluacion.controller;

import com.ivan.bci.evaluacion.dto.UserResponseDto;
import com.ivan.bci.evaluacion.model.UserModel;
import com.ivan.bci.evaluacion.dto.UserRequestDto;
import com.ivan.bci.evaluacion.service.JwtService;
import com.ivan.bci.evaluacion.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController
{

    private UserService userService;

    private JwtService jwtService;

    /**
     * Endpoint post para la creación de un usuario
     *
     * @param userRequestDto request de creación de usuario
     * @return json con UserResponseDto o mensaje de error
     */
    @Operation(summary = "Crea un nuevo usuario", description = "Retorna un nuevo usuario")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Usuario creado con exito",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))}
            ),
            @ApiResponse(responseCode = "400", description = "Hubo un error (mal contraseña, mail o usuario ya existente. \n" +
                    "El body del response tendrá el siguiente formato {\"mensaje\": \"mensaje de error\"}",
                    content = {@Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"mensaje\": \"mensaje de error\"}"))}
            )
    })
    public ResponseEntity<Object> newUser(
            @RequestBody
            @Parameter(name = "userRequest", description = "Request con el formato descrito en la consigna")
            UserRequestDto userRequestDto)
    {
        try
        {
            UserModel userModel = userService.addUser(userRequestDto);

            UserResponseDto userResponseDto = convertToDto(userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage(e.getMessage()));
        }
    }

    private String errorMessage(String message)
    {
        return "{\"mensaje\": \"" + message + "\"";
    }

    private UserResponseDto convertToDto(UserModel userModel)
    {
        String token = userModel.getToken();
        Date expiration = jwtService.extractExpiration(token);
        Date issuedAt = jwtService.extractClaim(token, Claims::getIssuedAt);
        ModelMapper modelMapper = new ModelMapper();

        UserResponseDto userResponseDto = modelMapper.map(userModel, UserResponseDto.class);
        userResponseDto.setLastLogin(issuedAt);
        userResponseDto.setActive(expiration.after(new Date()));

        return  userResponseDto;
    }

}
