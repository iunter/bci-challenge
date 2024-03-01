package com.ivan.bci.evaluacion.config;

import com.fasterxml.classmate.TypeResolver;
import com.ivan.bci.evaluacion.dto.ErrorMessageDto;
import com.ivan.bci.evaluacion.dto.UserResponseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SpringFoxConfig
{
    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(
                        typeResolver.resolve(UserResponseDto.class),
                        typeResolver.resolve(ErrorMessageDto.class)
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ivan.bci.evaluacion"))
                .paths(PathSelectors.any())
                .build();
    }
}
