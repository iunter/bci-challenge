package com.ivan.bci.evaluacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity(name = "phones")
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "phones")
@Getter
@Setter
@ApiModel
public class Phone
{
    @Id
    private int number;

    private int cityCode;

    private int countryCode;

    @JsonIgnore
    @ManyToOne
    private User user;

}
