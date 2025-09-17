package Hexa.example.demo.TransferenciaTests;

import adapters.web.TransferenciaController;
import adapters.web.mappers.TransferenciaMapper;
import domain.model.Transferencia;
import domain.ports.in.TransferenciaUC;
import dto.TransferenciaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferenciaControllerTest {

    private TransferenciaUC transferenciaUC;
    private TransferenciaController controller;
    private TransferenciaMapper mapper;


    @BeforeEach
    void setup() {
        transferenciaUC = Mockito.mock(TransferenciaUC.class);
        mapper = Mockito.mock(TransferenciaMapper.class);
        controller = new TransferenciaController(transferenciaUC, mapper);
    }

    @Test
    void getTransferencias_Pass() throws Exception {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);
        TransferenciaDTO dto = new TransferenciaDTO(12, 1L, "cuentadebito", "cuentacredito", fecha);

        Mockito.when(transferenciaUC.getTransferencias()).thenReturn(List.of(transferencia));
        Mockito.when(mapper.transferenciaToDTO(Mockito.any(Transferencia.class))).thenReturn(dto);

        List<TransferenciaDTO> result = controller.getTransferencias();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getEmpresa());
    }

    @Test
    void insertTransferencia_Pass() throws Exception {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 12L, "cuentadebito", "cuentacredito", fecha);
        TransferenciaDTO input = new TransferenciaDTO(12, 12L, "cuentadebito", "cuentacredito", fecha);

        Mockito.when(transferenciaUC.insertTransferencia(Mockito.any(Transferencia.class))).thenReturn(transferencia);
        Mockito.when(mapper.transferenciaToDomain(Mockito.any(TransferenciaDTO.class))).thenReturn(transferencia);
        Mockito.when(mapper.transferenciaToDTO(Mockito.any(Transferencia.class))).thenReturn(input);

        ResponseEntity<TransferenciaDTO> response = controller.insertTransferencia(input);

        TransferenciaDTO result = response.getBody();

        assertEquals(12L, result.getEmpresa());
    }

    @Test
    void getLastTransferencias_Pass() throws Exception {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);
        TransferenciaDTO dto = new TransferenciaDTO(12, 1L, "cuentadebito", "cuentacredito", fecha);

        Mockito.when(transferenciaUC.getLastTransferencias()).thenReturn(List.of(transferencia));
        Mockito.when(mapper.transferenciaToDTO(Mockito.any(Transferencia.class))).thenReturn(dto);

        List<TransferenciaDTO> result = controller.getLastTransferencias();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getEmpresa());
    }
}
