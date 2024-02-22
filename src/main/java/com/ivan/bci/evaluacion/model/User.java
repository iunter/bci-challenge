package com.ivan.bci.evaluacion.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Phone> phoneList;

    private Date created;

    private Date modified;

    private Date lastLogin;

    private String token;

    private boolean isActive;

    public void setPhoneList(List<Phone> phoneList)
    {
        this.phoneList = phoneList;
        for(Phone phone: this.phoneList)
        {
            phone.setUser(this);
        }
    }

}
