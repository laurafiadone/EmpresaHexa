package Hexa.example.demo.EmpresaTests;

import domain.model.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmpresaTest {
    private Clock clock;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(
                Instant.parse("2025-09-30T10:15:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")
        );
    }

    @Test
    void empresaAllArguments() {
        LocalDate fecha = LocalDate.now(clock);
        Empresa empresa = new Empresa(1L, "Empresa2", LocalDate.now());

        assertEquals("Empresa2", empresa.getRazon_social());
        assertEquals(1L, empresa.getCuit());
    }
}
