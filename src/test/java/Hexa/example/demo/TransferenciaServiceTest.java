package Hexa.example.demo;

import domain.model.Transferencia;
import domain.ports.out.TransferenciaRepoPort;
import domain.service.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferenciaServiceTest {
    private TransferenciaService transferenciaService;
    private TransferenciaRepoPort transferenciaRepoPort;

    @BeforeEach
    void setUp() {
        transferenciaRepoPort = Mockito.mock(TransferenciaRepoPort.class);
        transferenciaService = new TransferenciaService(transferenciaRepoPort);
    }

    @Test
    void getTransferenciasPass() {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        when(transferenciaRepoPort.findAll()).thenReturn(List.of(transferencia));

        List<Transferencia> transferenciasList = transferenciaService.getTransferencias();

        assertEquals(1, transferenciasList.size());
        assertEquals(12, transferenciasList.get(0).getImporte());
    }

    @Test
    void insertTransferenciaPass() {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        when(transferenciaRepoPort.save(transferencia)).thenReturn(transferencia);

        Transferencia transferenciaSave = transferenciaService.insertTransferencia(transferencia);

        assertEquals(1, transferenciaSave.getEmpresa());
        verify(transferenciaRepoPort).save(transferencia);
    }

    @Test
    void getLastTransferenciasPass() {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        when(transferenciaRepoPort.getTransferenciasByLastPeriod()).thenReturn(List.of(transferencia));

        List<Transferencia> transferenciaList = transferenciaService.getLastTransferencias();

        assertEquals(1, transferenciaList.size());
    }

    @Test
    void getTransferenciasByCuitPass() {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        when(transferenciaRepoPort.findByEmpresaCuit(1L)).thenReturn(List.of(transferencia));

        List<Transferencia> transferenciaList = transferenciaService.getTransferenciasByCuit(1L);

        assertEquals(1, transferenciaList.size());
    }
}
