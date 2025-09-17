package Hexa.example.demo.TransferenciaTests;

import adapters.persistence.TransferenciaAdapter;
import adapters.persistence.mappers.TransferenciaEntityMapper;
import domain.model.Transferencia;
import infrastructure.SpringDataRepositories.SpringDataTransferenciaRepo;
import infrastructure.entity.TransferenciaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferenciaAdapterTest {

    private SpringDataTransferenciaRepo transferenciaRepo;
    private TransferenciaAdapter transferenciaAdapter;
    private TransferenciaEntityMapper transferenciaEntityMapper;
    private Clock clock;

    @BeforeEach
    void setUp() {
        transferenciaRepo = Mockito.mock(SpringDataTransferenciaRepo.class);
        transferenciaEntityMapper = Mockito.mock(TransferenciaEntityMapper.class);
        clock = Clock.fixed(
                Instant.parse("2025-09-30T10:15:30.00Z"),
                ZoneId.of("America/Argentina/Buenos_Aires")
        );
        transferenciaAdapter = new TransferenciaAdapter(transferenciaRepo, transferenciaEntityMapper, clock);
    }

    @Test
    void findAll_Pass() {
        TransferenciaEntity entity = new TransferenciaEntity();
        entity.setImporte(10);
        entity.setEmpresa(10L);
        entity.setCuentaDebito("A");
        entity.setCuentaCredito("B");
        LocalDate today = LocalDate.now(clock);
        Transferencia transferencia = new Transferencia(100, 10L, "debito", "credito", today);

        when(transferenciaRepo.findAll()).thenReturn(List.of(entity));
        when(transferenciaEntityMapper.transferenciaToDomain(entity)).thenReturn(transferencia);

        List<Transferencia> listTransferencias = transferenciaAdapter.findAll();

        assertEquals(1, listTransferencias.size());
    }

    @Test
    void save_Pass() {
        LocalDate today = LocalDate.now(clock);
        Transferencia transferencia = new Transferencia(10, 10L, "A", "B", today);

        TransferenciaEntity entity = new TransferenciaEntity();
        entity.setImporte(10);
        entity.setEmpresa(10L);
        entity.setCuentaDebito("A");
        entity.setCuentaCredito("B");

        when(transferenciaEntityMapper.transferenciaToEntity(transferencia)).thenReturn(entity);
        when(transferenciaRepo.save(any(TransferenciaEntity.class))).thenReturn(entity);
        when(transferenciaEntityMapper.transferenciaToDomain(entity)).thenReturn(transferencia);

        Transferencia transferenciaSave = transferenciaAdapter.save(transferencia);

        assertEquals(10, transferenciaSave.getEmpresa());
        verify(transferenciaRepo).save(any(TransferenciaEntity.class));
    }

    @Test
    void getTransferenciasByLastPeriod_Pass() {
        TransferenciaEntity entity = new TransferenciaEntity();
        entity.setImporte(10);
        entity.setEmpresa(10L);
        entity.setCuentaDebito("A");
        entity.setCuentaCredito("B");

        LocalDate fecha_from = LocalDate.now(clock).minusMonths(1);
        LocalDate fecha_to = LocalDate.now(clock);


        Transferencia transferencia = new Transferencia(100, 10L, "debito", "credito", LocalDate.now());

        when(transferenciaRepo.findByFechaBetween(fecha_from, fecha_to)).thenReturn(List.of(entity));
        when(transferenciaEntityMapper.transferenciaToDomain(entity)).thenReturn(transferencia);

        List<Transferencia> transferenciasList = transferenciaAdapter.getTransferenciasByLastPeriod();
        assertEquals(1, transferenciasList.size());
    }

    @Test
    void findByEmpresaCuit_Pass() {
        TransferenciaEntity entity = new TransferenciaEntity();
        entity.setImporte(10);
        entity.setEmpresa(10L);
        entity.setCuentaDebito("A");
        entity.setCuentaCredito("B");

        LocalDate today = LocalDate.now(clock);

        Transferencia transferencia = new Transferencia(10, 10L, "A", "B", today);

        when(transferenciaRepo.findByEmpresa(10L)).thenReturn(List.of(entity));
        when(transferenciaEntityMapper.transferenciaToDomain(entity)).thenReturn(transferencia);

        List<Transferencia> transferenciasList = transferenciaAdapter.findByEmpresaCuit(10L);
        assertEquals(1, transferenciasList.size());
    }
}
