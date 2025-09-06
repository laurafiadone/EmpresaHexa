package Hexa.example.demo;

import application.adaptador.TransferenciaAdapter;
import domain.model.Transferencia;
import infrastructure.SpringDataTransferenciaRepo;
import infrastructure.TransferenciaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferenciaAdapterTest {

    private SpringDataTransferenciaRepo transferenciaRepo;
    private TransferenciaAdapter transferenciaAdapter;

    @BeforeEach
    void setUp() {
        transferenciaRepo = Mockito.mock(SpringDataTransferenciaRepo.class);
        transferenciaAdapter = new TransferenciaAdapter(transferenciaRepo);
    }

    @Test
    void findAll_Pass() {
        TransferenciaEntity entity = new TransferenciaEntity();
        entity.setImporte(10);
        entity.setEmpresa(10L);
        entity.setCuentaDebito("A");
        entity.setCuentaCredito("B");

        when(transferenciaRepo.findAll()).thenReturn(List.of(entity));

        List<Transferencia> listTransferencias = transferenciaAdapter.findAll();

        assertEquals(1, listTransferencias.size());
    }

    @Test
    void save_Pass() {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(10, 10L, "A", "B", fecha);

        TransferenciaEntity entity = new TransferenciaEntity();
        entity.setImporte(10);
        entity.setEmpresa(10L);
        entity.setCuentaDebito("A");
        entity.setCuentaCredito("B");

        when(transferenciaRepo.save(any(TransferenciaEntity.class))).thenReturn(entity);

       Transferencia transferenciaSave =  transferenciaAdapter.save(transferencia);

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

        LocalDate fecha_from = LocalDate.now().minusMonths(1);
        LocalDate fecha_to = LocalDate.now();


        when(transferenciaRepo.findByFechaBetween(fecha_from, fecha_to)).thenReturn(List.of(entity));

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

        when(transferenciaRepo.findByEmpresa(10L)).thenReturn(List.of(entity));

        List<Transferencia> transferenciasList = transferenciaAdapter.findByEmpresaCuit(10L);
        assertEquals(1, transferenciasList.size());
    }
}
