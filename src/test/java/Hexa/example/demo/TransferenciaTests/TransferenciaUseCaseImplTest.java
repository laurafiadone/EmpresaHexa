package Hexa.example.demo.TransferenciaTests;

import application.TransferenciaUseCaseImpl;
import domain.model.Transferencia;
import domain.ports.out.TransferenciaRepoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferenciaUseCaseImplTest {

    private TransferenciaUseCaseImpl useCaseImpl;
    private TransferenciaRepoPort repoPort;
    private Clock clock;

    @BeforeEach
    void setUp() {
        repoPort = Mockito.mock(TransferenciaRepoPort.class);
        useCaseImpl = new TransferenciaUseCaseImpl(repoPort);
        clock = Clock.fixed(
                Instant.parse("2025-09-30T10:15:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")
        );
    }

    @Test
    void listTransferencias_isEmpty() {
        when(repoPort.findAll()).thenReturn(Collections.emptyList());

        List<Transferencia> listTransferencias = useCaseImpl.getTransferencias();

        assertTrue(listTransferencias.isEmpty());
    }

    @Test
    void getTransferenciasLastMonth_isEmpty() {
        when(repoPort.getTransferenciasByLastPeriod()).thenReturn(Collections.emptyList());

        List<Transferencia> listTransferencias = useCaseImpl.getLastTransferencias();

        assertTrue(listTransferencias.isEmpty());
    }

    @Test
    void getTransferenciasPass() {
        LocalDate fecha = LocalDate.now(clock);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        when(repoPort.findAll()).thenReturn(List.of(transferencia));

        List<Transferencia> transferenciasList = useCaseImpl.getTransferencias();

        assertEquals(1, transferenciasList.size());
        assertEquals(12, transferenciasList.get(0).getImporte());
    }

    @Test
    void insertTransferenciaPass() {
        LocalDate fecha = LocalDate.now(clock);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        when(repoPort.save(transferencia)).thenReturn(transferencia);

        Transferencia transferenciaSave = useCaseImpl.insertTransferencia(transferencia);

        assertEquals(1, transferenciaSave.getEmpresa());
        verify(repoPort).save(transferencia);
    }

    @Test
    void getLastTransferenciasPass() {
        LocalDate fecha = LocalDate.now(clock);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        when(repoPort.getTransferenciasByLastPeriod()).thenReturn(List.of(transferencia));

        List<Transferencia> transferenciaList = useCaseImpl.getLastTransferencias();

        assertEquals(1, transferenciaList.size());
    }

    @Test
    void getTransferenciasByCuitPass() {
        LocalDate fecha = LocalDate.now(clock);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        when(repoPort.findByEmpresaCuit(1L)).thenReturn(List.of(transferencia));

        List<Transferencia> transferenciaList = useCaseImpl.getTransferenciasByCuit(1L);

        assertEquals(1, transferenciaList.size());
    }
}
