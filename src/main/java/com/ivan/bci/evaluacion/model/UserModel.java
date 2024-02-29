package com.ivan.bci.evaluacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Model de un usuario para ser persistido en base de datos
 */
@Entity(name = "users")
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Getter
@Setter
@Builder
@ApiModel
public class UserModel
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(name = "Id del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userId;

    @ApiModelProperty(name = "Nombre del usuario", example = "Juan Gomez")
    private String name;

    @ApiModelProperty(name = "Email del usuario", example = "juangomez@example.com")
    private String email;

    @JsonIgnore
    @ApiModelProperty(name = "Password", example = "password123")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @ApiModelProperty(name = "Lista de telefonos asociados")
    private List<PhoneModel> phoneList;

    @ApiModelProperty(name = "Fecha de creación")
    private Date created;

    @ApiModelProperty(name = "Fecha última modificación")
    private Date modified;

    @ApiModelProperty(name = "Token JWT del usuario")
    private String token;

}
