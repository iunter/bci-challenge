package com.ivan.bci.evaluacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EvaluacionApplication
{

	public static void main(String[] args)
	{
		try
		{
			SpringApplication.run(EvaluacionApplication.class, args);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
