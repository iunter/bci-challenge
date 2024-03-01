package com.ivan.bci.evaluacion;

import com.ivan.bci.evaluacion.controller.UserControllerAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(UserControllerAdvice.class)
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
