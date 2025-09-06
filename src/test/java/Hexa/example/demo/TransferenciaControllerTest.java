package Hexa.example.demo;

import application.controller.TransferenciaController;
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

    @BeforeEach
    void setup() {
        transferenciaUC = Mockito.mock(TransferenciaUC.class);
        controller = new TransferenciaController(transferenciaUC);
    }

    @Test
    void getTransferencias_Pass() throws Exception {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        Mockito.when(transferenciaUC.getTransferencias()).thenReturn(List.of(transferencia));

   /*     mockMvc.perform(get("/api/transferencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empresa").value(1));
    }*/
        List<TransferenciaDTO> result = controller.getTransferencias();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getEmpresa());
    }

    @Test
    void insertTransferencia_Pass() throws Exception {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 12L, "cuentadebito", "cuentacredito", fecha);

        Mockito.when(transferenciaUC.insertTransferencia(Mockito.any(Transferencia.class))).thenReturn(transferencia);

      /*  String jsonBody = """
        {
          "importe": 10,
          "empresa": 12,
          "cuentaDebito": "A"
          "cuentaCredito": "C"
        }
        """;

        mockMvc.perform(post("/api/transferencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empresa").value(12));*/

        TransferenciaDTO input = new TransferenciaDTO(12, 12L, "cuentadebito", "cuentacredito", fecha);

        // llamamos al controlador
        ResponseEntity<TransferenciaDTO> response = controller.insertTransferencia(input);

        // obtenemos el DTO del body
        TransferenciaDTO result = response.getBody();

        // verificamos los campos
        assertEquals(12L, result.getEmpresa());
    }

    @Test
    void getLastTransferencias_Pass() throws Exception {
        LocalDate fecha = LocalDate.of(2025, 5, 10);
        Transferencia transferencia = new Transferencia(12, 1L, "cuentadebito", "cuentacredito", fecha);

        Mockito.when(transferenciaUC.getLastTransferencias()).thenReturn(List.of(transferencia));
/*
        mockMvc.perform(get("/api/transferencias/period/last-transferencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empresa").value(1));*/

        List<TransferenciaDTO> result = controller.getLastTransferencias();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getEmpresa());
    }
}
