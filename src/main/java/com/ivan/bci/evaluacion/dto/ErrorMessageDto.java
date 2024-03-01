package com.ivan.bci.evaluacion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class ErrorMessageDto
{
	@Schema(name = "Mensaje de error", example = "La contraseña no cumple con los requerimientos")
	private String message;
}
