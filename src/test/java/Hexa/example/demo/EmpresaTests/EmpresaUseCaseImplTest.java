package Hexa.example.demo.EmpresaTests;

import application.EmpresaUseCaseImpl;
import domain.CuitException;
import domain.model.Empresa;
import domain.ports.out.EmpresaRepoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmpresaUseCaseImplTest {

    private EmpresaUseCaseImpl empresaImpl;
    private EmpresaRepoPort repoPort;
    private Clock clock;

    @BeforeEach
    void setUp() {
        repoPort = Mockito.mock(EmpresaRepoPort.class);
        empresaImpl = new EmpresaUseCaseImpl(repoPort);
        clock = Clock.fixed(
                Instant.parse("2025-09-30T10:15:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")
        );
    }

    @Test
    void listEmpresas_isEmpty() {
        when(repoPort.findAll()).thenReturn(Collections.emptyList());

        List<Empresa> listEmpresas = empresaImpl.listEmpresas();

        assertTrue(listEmpresas.isEmpty());
    }

    @Test
    void getEmpresasByLastMonth_isEmpty() {
        when(repoPort.findByFechaAdhesionBetween()).thenReturn(Collections.emptyList());

        List<Empresa> listEmpresas = empresaImpl.getEmpresasByLastMonth();

        assertTrue(listEmpresas.isEmpty());
    }

    @Test
    void insertEmpresa_duplicado_fail() {
        LocalDate today = LocalDate.now(clock);
        Empresa empresa = new Empresa(10L, "CuitDuplicado", today);

        when(repoPort.findByCuit(10L)).thenReturn(Optional.of(empresa));

        assertThrows(CuitException.class, () -> empresaImpl.insertEmpresa(empresa));
    }

    @Test
    void listEmpresas_Pass() {
        LocalDate today = LocalDate.now(clock);
        Empresa empresa = new Empresa(10L, "CuitDuplicado", today);
        when(repoPort.findAll()).thenReturn(List.of(empresa));

        List<Empresa> listEmpresas = empresaImpl.listEmpresas();

        assertEquals(1, listEmpresas.size());
    }

    @Test
    void getEmpresasByCuit_Pass() {
        LocalDate today = LocalDate.now(clock);
        Empresa empresa = new Empresa(10L, "CuitDuplicado", today);
        when(repoPort.findByCuit(10L)).thenReturn(Optional.of(empresa));

        Optional<Empresa> listEmpresas = empresaImpl.getEmpresasByCuit(10L);

        assertTrue(listEmpresas.isPresent());
    }

    @Test
    void insertEmpresa_Pass() {
        LocalDate today = LocalDate.now(clock);
        Empresa empresa = new Empresa(10L, "EmpresaTest", today);

        when(repoPort.save(empresa)).thenReturn(empresa);

        Empresa empresaSave = empresaImpl.insertEmpresa(empresa);
        assertEquals("EmpresaTest", empresaSave.getRazon_social());
    }

    @Test
    void listEmpresasPass() {
        LocalDate today = LocalDate.now(clock);
        Empresa empresa = new Empresa(1L, "Empresa1", today);

        when(repoPort.findAll()).thenReturn(List.of(empresa));

        List<Empresa> listEmpresas = empresaImpl.listEmpresas();

        assertEquals(1, listEmpresas.size());
        assertEquals("Empresa1", listEmpresas.get(0).getRazon_social());
    }

    @Test
    void insertEmpresaPass() {
        LocalDate today = LocalDate.now(clock);
        Empresa empresa = new Empresa(1L, "Empresa2", today);

        when(repoPort.save(Mockito.any(Empresa.class))).thenReturn(empresa);

        Empresa empresaSave = empresaImpl.insertEmpresa(empresa);

        assertEquals("Empresa2", empresaSave.getRazon_social());
        verify(repoPort).save(Mockito.any(Empresa.class));
    }

    @Test
    void getEmpredadByCuitPass() {
        LocalDate today = LocalDate.now(clock);
        Empresa empresa = new Empresa(3L, "Empresa2", today);

        when(repoPort.findByCuit(3L)).thenReturn(Optional.of(empresa));

        Optional<Empresa> empresaByCuit = empresaImpl.getEmpresasByCuit(3L);

        assertTrue(empresaByCuit.isPresent());
        assertEquals(3L, empresaByCuit.get().getCuit());
    }
}
