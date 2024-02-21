package com.ivan.bci.evaluacion.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest
{

    private String name;

    private String email;

    private String password;

    private List<Phone> phoneList;

}
