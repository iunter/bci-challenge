package com.ivan.bci.evaluacion

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class EvaluacionApplicationTest extends Specification
{
    def "context loads"() {
        expect:
        true
    }
}
