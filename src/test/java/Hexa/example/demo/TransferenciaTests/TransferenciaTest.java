package Hexa.example.demo.TransferenciaTests;

import domain.model.Transferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferenciaTest {
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
        Transferencia transferencia = new Transferencia(10, 10L, "A", "B", fecha);

        assertEquals(10, transferencia.getEmpresa());
        assertEquals(10L, transferencia.getEmpresa());
    }
}
