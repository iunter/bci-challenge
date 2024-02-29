package com.ivan.bci.evaluacion.repository;

import com.ivan.bci.evaluacion.model.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhoneRepository extends JpaRepository<PhoneModel, Integer>
{
}
