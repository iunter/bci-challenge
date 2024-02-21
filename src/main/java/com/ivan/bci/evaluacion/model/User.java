package com.ivan.bci.evaluacion.model;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "users")
@Getter
@Builder
@ApiModel
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Phone> phoneList;

    private Date created;

    private Date modified;

    private Date lastLogin;

    private String token;

    private boolean isActive;

}
