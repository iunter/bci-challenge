package com.ivan.bci.evaluacion.dto;

import com.ivan.bci.evaluacion.model.PhoneModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * DTO para el response de un usuario nuevo
 * No se extiende de {@link com.ivan.bci.evaluacion.model.UserModel} para no acoplar las dos clases
 */
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class UserResponseDto
{

	@ApiModelProperty(name = "Id del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID userId;

	@ApiModelProperty(name = "Nombre del usuario", example = "Juan Gomez")
	private String name;

	@ApiModelProperty(name = "Email del usuario", example = "juangomez@example.com")
	private String email;

	@ApiModelProperty(name = "Lista de telefonos asociados")
	private List<PhoneModel> phoneList;

	@ApiModelProperty(name = "Fecha de creación")
	private Date created;

	@ApiModelProperty(name = "Fecha última modificación")
	private Date modified;

	@ApiModelProperty(name = "Fecha último logueo")
	private Date lastLogin;

	@ApiModelProperty(name = "Token JWT del usuario")
	private String token;

	@ApiModelProperty(name = "Indica si el token esta activo", example = "true")
	private boolean isActive;

}
