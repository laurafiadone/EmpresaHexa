package Hexa.example.demo;

import application.controller.EmpresaController;
import domain.model.Empresa;
import domain.ports.in.EmpresaUC;
import dto.EmpresaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmpresaControllerTest {

    private EmpresaUC empresaUC;
    private EmpresaController empresaController;

    @BeforeEach
    void setup() {
        empresaUC = Mockito.mock(EmpresaUC.class);
        empresaController = new EmpresaController(empresaUC);
    }

    @Test
    void listEmpresas_Pass() throws Exception {
        Empresa empresa = new Empresa(1L, "EmpresaA", LocalDate.now());

        Mockito.when(empresaUC.listEmpresas()).thenReturn(List.of(empresa));

        List<EmpresaDTO> result = empresaController.listEmpresas();

        assertEquals(1, result.size());
    }

    @Test
    void searchByCuit_Pass() throws Exception {
        Empresa empresa = new Empresa(1L, "EmpresaA", LocalDate.now());

        Mockito.when(empresaUC.getEmpresasByCuit(1L)).thenReturn(Optional.of(empresa));

        ResponseEntity<EmpresaDTO> response = empresaController.searchByCuit(1L);
        EmpresaDTO result = response.getBody();

        assertEquals("EmpresaA", result.getRazon_social());
    }

    @Test
    void insertEmpresa_Pass() throws Exception {
        Empresa empresa = new Empresa(1L, "EmpresaA", LocalDate.now());

        Mockito.when(empresaUC.insertEmpresa(Mockito.any(Empresa.class))).thenReturn(empresa);

        EmpresaDTO input = new EmpresaDTO(1L, "Acme", LocalDate.of(2023, 5, 10));
        ResponseEntity<EmpresaDTO> response = empresaController.insertEmpresa(input);
        EmpresaDTO result = response.getBody();

        assertEquals("EmpresaA", result.getRazon_social());
    }

    @Test
    void getEmpresasLastMonth_Pass() throws Exception {
        Empresa empresa = new Empresa(1L, "EmpresaA", LocalDate.now());

        Mockito.when(empresaUC.getEmpresasByLastMonth()).thenReturn(List.of(empresa));

        List<?> result = empresaController.getEmpresasLastMonth();

        assertEquals(1, result.size());
    }
}
