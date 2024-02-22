package com.ivan.bci.evaluacion.repository;

import com.ivan.bci.evaluacion.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhoneRepository extends JpaRepository<Phone, Integer>
{
}
