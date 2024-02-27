package com.ivan.bci.evaluacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DTO representando un teléfono
 */
@Entity(name = "phones")
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phones")
@Getter
@Setter
@Builder
@ApiModel
public class Phone
{
    @Id
    @Schema(name = "Numero de telefono", example = "12345678", required = true)
    private int number;

    @Schema(name = "Codigo de ciudad", example = "1", required = true)
    private int cityCode;

    @Schema(name = "Codigo de pais", example = "54", required = true)
    private int countryCode;

    @JsonIgnore
    @ManyToOne
    private User user;

}
