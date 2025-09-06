package Hexa.example.demo;

import application.adaptador.EmpresaAdapter;
import domain.model.Empresa;
import infrastructure.EmpresaEntity;
import infrastructure.SpringDataEmpresaRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
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

    @BeforeEach
    void setUp() {
        repoMock = Mockito.mock(SpringDataEmpresaRepo.class);
        empresaAdapter = new EmpresaAdapter(repoMock);
    }

    @Test
    void findAll_Entities() {
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(1L);
        empresaEntity.setRazon_social("EmpresaTest");
        empresaEntity.setFechaAdhesion(LocalDate.now());

        when(repoMock.findAll()).thenReturn(List.of(empresaEntity));

        List<Empresa> empresas = empresaAdapter.findAll();

        assertEquals(1, empresas.size());
        assertEquals("EmpresaTest", empresas.get(0).getRazon_social());
    }

    @Test
    void save_Empresa() {
        Empresa empresa = new Empresa(1L, "EmpresaTest", LocalDate.now());

        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(1L);
        empresaEntity.setRazon_social("EmpresaTest");
        empresaEntity.setFechaAdhesion(LocalDate.now());

        when(repoMock.save(any(EmpresaEntity.class))).thenReturn(empresaEntity);

        Empresa empresaSave = empresaAdapter.save(empresa);

        assertEquals("EmpresaTest", empresaSave.getRazon_social());
        verify(repoMock).save(any(EmpresaEntity.class));
    }

    @Test
    void findByCuit_Pass() {
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(1L);
        empresaEntity.setRazon_social("EmpresaTest");
        empresaEntity.setFechaAdhesion(LocalDate.now());

        when(repoMock.findById(1L)).thenReturn(Optional.of(empresaEntity));

        Optional<Empresa> byCuit = empresaAdapter.findByCuit(1L);

        assertTrue(byCuit.isPresent());
    }

    @Test
    void findByFechaAdhesionBetween_Pass() {
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(1L);
        empresaEntity.setRazon_social("EmpresaTest");
        empresaEntity.setFechaAdhesion(LocalDate.now());

        when(repoMock.findByFechaAdhesionBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(List.of(empresaEntity));
        List<Empresa> listEmpresa = empresaAdapter.findByFechaAdhesionBetween();

        assertEquals(1, listEmpresa.size());
    }
}
