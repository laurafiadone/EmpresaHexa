package Hexa.example.demo.EmpresaTests;

import adapters.persistence.EmpresaAdapter;
import adapters.persistence.mappers.EmpresaEntityMapper;
import domain.model.Empresa;
import infrastructure.SpringDataRepositories.SpringDataEmpresaRepo;
import infrastructure.entity.EmpresaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmpresaAdapterTest {

    private SpringDataEmpresaRepo repoMock;
    private EmpresaAdapter empresaAdapter;
    private EmpresaEntityMapper entityMapper;
    private Clock clock;

    @BeforeEach
    void setUp() {
        repoMock = Mockito.mock(SpringDataEmpresaRepo.class);
        entityMapper = Mockito.mock(EmpresaEntityMapper.class);
        clock = Clock.fixed(
                Instant.parse("2025-09-30T10:15:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")
        );
        empresaAdapter = new EmpresaAdapter(repoMock, entityMapper, clock);
    }

    @Test
    void findAll_Entities() {
        LocalDate today = LocalDate.now(clock);
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(1L);
        empresaEntity.setRazonSocial("EmpresaTest");
        empresaEntity.setFechaAdhesion(today);
        Empresa empresa = new Empresa(1L, "EmpresaTest", LocalDate.now());

        when(repoMock.findAll()).thenReturn(List.of(empresaEntity));
        when(entityMapper.empresaToDomain(empresaEntity)).thenReturn(empresa);

        List<Empresa> empresas = empresaAdapter.findAll();

        assertEquals(1, empresas.size());
        assertEquals("EmpresaTest", empresas.get(0).getRazon_social());
    }

    @Test
    void save_Empresa() {
        Empresa empresa = new Empresa(1L, "EmpresaTest", LocalDate.now());

        LocalDate today = LocalDate.now(clock);
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(1L);
        empresaEntity.setRazonSocial("EmpresaTest");
        empresaEntity.setFechaAdhesion(today);

        when(entityMapper.empresaToEntity(empresa)).thenReturn(empresaEntity);
        when(repoMock.save(any(EmpresaEntity.class))).thenReturn(empresaEntity);
        when(entityMapper.empresaToDomain(empresaEntity)).thenReturn(empresa);

        Empresa empresaSave = empresaAdapter.save(empresa);

        assertEquals("EmpresaTest", empresaSave.getRazon_social());
        verify(repoMock).save(any(EmpresaEntity.class));
    }

    @Test
    void findByCuit_Pass() {
        Empresa empresa = new Empresa(1L, "EmpresaTest", LocalDate.now());

        LocalDate today = LocalDate.now(clock);
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(1L);
        empresaEntity.setRazonSocial("EmpresaTest");
        empresaEntity.setFechaAdhesion(today);

        when(repoMock.findById(1L)).thenReturn(Optional.of(empresaEntity));
        when(entityMapper.empresaToDomain(empresaEntity)).thenReturn(empresa);

        Optional<Empresa> byCuit = empresaAdapter.findByCuit(1L);

        assertTrue(byCuit.isPresent());
    }

    @Test
    void findByFechaAdhesionBetween_Pass() {
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(1L);
        empresaEntity.setRazonSocial("EmpresaTest");
        empresaEntity.setFechaAdhesion(LocalDate.now());

        LocalDate fechaTo = LocalDate.now(clock);
        LocalDate fechaFrom = fechaTo.minusMonths(1);

        Empresa empresa = new Empresa(1L, "EmpresaTest", LocalDate.now());

        when(repoMock.findByFechaAdhesionBetween(fechaFrom, fechaTo))
                .thenReturn(List.of(empresaEntity));
        when(entityMapper.empresaToDomain(empresaEntity)).thenReturn(empresa);

        List<Empresa> listEmpresa = empresaAdapter.findByFechaAdhesionBetween();

        assertEquals(1, listEmpresa.size());
    }

    @Test
    void findByFechaAdhesionBetween_EndOfMonth() {
        Clock endOfMonthClock = Clock.fixed(
                Instant.parse("2025-09-30T23:59:59Z"), ZoneId.of("UTC")
        );
        empresaAdapter = new EmpresaAdapter(repoMock, entityMapper, endOfMonthClock);

        EmpresaEntity entity = new EmpresaEntity();
        entity.setCuit(99L);
        entity.setRazonSocial("FinMes");
        entity.setFechaAdhesion(LocalDate.now(endOfMonthClock));

        when(repoMock.findByFechaAdhesionBetween(any(), any()))
                .thenReturn(List.of(entity));
        when(entityMapper.empresaToDomain(entity))
                .thenReturn(new Empresa(99L, "FinMes", LocalDate.now(endOfMonthClock)));

        List<Empresa> result = empresaAdapter.findByFechaAdhesionBetween();

        assertEquals(1, result.size());
        assertEquals("FinMes", result.get(0).getRazon_social());
    }
}
