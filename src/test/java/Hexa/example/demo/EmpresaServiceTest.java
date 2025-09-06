package Hexa.example.demo;

import domain.model.Empresa;
import domain.ports.out.EmpresaRepoPort;
import domain.service.EmpresaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmpresaServiceTest {

    private EmpresaService empresaService;
    private EmpresaRepoPort empresaRepoPort;

    @BeforeEach
    void setUp() {
        empresaRepoPort = Mockito.mock(EmpresaRepoPort.class);
        empresaService = new EmpresaService(empresaRepoPort);
    }

    @Test
    void listEmpresasPass() {
        Empresa empresa = new Empresa(1L, "Empresa1", LocalDate.now());

        when(empresaRepoPort.findAll()).thenReturn(List.of(empresa));

        List<Empresa> listEmpresas = empresaService.listEmpresas();

        assertEquals(1, listEmpresas.size());
        assertEquals("Empresa1", listEmpresas.get(0).getRazon_social());
    }

    @Test
    void insertEmpresaPass() {
        Empresa empresa = new Empresa(1L, "Empresa2", LocalDate.now());

        when(empresaRepoPort.save(Mockito.any(Empresa.class))).thenReturn(empresa);

        Empresa empresaSave = empresaService.insertEmpresa(empresa);

        assertEquals("Empresa2", empresaSave.getRazon_social());
        verify(empresaRepoPort).save(Mockito.any(Empresa.class));    }

    @Test
    void getEmpredadByCuitPass() {
        Empresa empresa = new Empresa(3L, "Empresa2", LocalDate.now());

        when(empresaRepoPort.findByCuit(3L)).thenReturn(Optional.of(empresa));

        Optional<Empresa> empresaByCuit = empresaService.getEmpresasByCuit(3L);

        assertTrue(empresaByCuit.isPresent());
        assertEquals(3L, empresaByCuit.get().getCuit());
    }
}
