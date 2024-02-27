package com.ivan.bci.evaluacion.model;



import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * DTO para el request de un usuario nuevo
 */
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class UserRequest
{
    @Schema(name = "Nombre del usuario", example = "Juan Gomez", required = true)
    private String name;

    @Schema(name = "Email del usuario", example = "juangomez@example.com", required = true)
    private String email;

    @Schema(name = "Password", example = "password123", required = true)
    private String password;

    @Schema(name = "Numeros de telefono pertenecientes al usuario", required = true)
    private List<Phone> phones;

}
