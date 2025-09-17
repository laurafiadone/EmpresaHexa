package adapters.web;

import adapters.web.mappers.TransferenciaMapper;
import domain.model.Transferencia;
import domain.ports.in.TransferenciaUC;
import dto.TransferenciaDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;


@RestController
@RequestMapping("/api/transferencias")
@Tag(name = "Transferencias", description = "Operaciones relacionadas con transferencias")
public class TransferenciaController {

    private TransferenciaUC transferenciaUC;
    private TransferenciaMapper transferenciaMapper;

    @Autowired
    public TransferenciaController(TransferenciaUC transferenciaUC, TransferenciaMapper transferenciaMapper) {
        this.transferenciaUC = transferenciaUC;
        this.transferenciaMapper = transferenciaMapper;
    }

    @GetMapping
    @Operation(
            summary = "Listar todas las Transferencias",
            description = """
                Devuelve una lista de transferencias registradas
        
                ###CURL:
                ```bash
                curl -X GET "http://localhost:8080/transferencias" -H "accept: application/json"
                ```
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de transferencias obtenido con éxito",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = """
                                    [
                                      {
                                        "id": 101,
                                        "importe": 1500,
                                        "empresa": "EmpresaA",
                                        "cuentaDebito": "1234",
                                        "cuentaCredito": "454564",
                                        "fecha": "2025-09-01"
                                      },
                                      {
                                        "id": 102,
                                        "importe": 2500,
                                        "empresa": "EmpresaB",
                                        "cuentaDebito": "45622",
                                        "cuentaCredito": "23678",
                                        "fecha": "2025-09-05"
                                      }
                                    ]
                                    """
                            )
                    )
            )
    })
    public List<TransferenciaDTO> getTransferencias() {
        return transferenciaUC.getTransferencias().stream().map(transferenciaMapper::transferenciaToDTO).toList();
    }

    @PostMapping
    @Operation(summary = "Agregar una nueva transferencia", description = "Agrega al sistema una nueva transferencia")
    public ResponseEntity<TransferenciaDTO> insertTransferencia(@RequestBody TransferenciaDTO in_Transferencia) {
        Transferencia transferencia_save = transferenciaMapper.transferenciaToDomain(in_Transferencia);
        Transferencia transferencia_creada = transferenciaUC.insertTransferencia(transferencia_save);

        return ResponseEntity.ok(transferenciaMapper.transferenciaToDTO(transferencia_creada));
    }

    @GetMapping("/period/last-transferencias")
    @Operation(
            summary = "Obtener Empresas y Transferencias",
            description = """
                Devuelve una lista las ultimas empresas que hicieron transferencias
        
                ###CURL:
                ```bash
                curl -X GET "http://localhost:8080/transferencias/period/last-transferencias" -H "accept: application/json"
                ```
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de empresas que hicieron ultimas transferencias ",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = """
                                    [
                                      {
                                        "id": 101,
                                        "importe": 1500,
                                        "empresa": "EmpresaA",
                                        "cuentaDebito": "1234",
                                        "cuentaCredito": "454564",
                                        "fecha": "2025-09-01"
                                      },
                                      {
                                        "id": 102,
                                        "importe": 2500,
                                        "empresa": "EmpresaB",
                                        "cuentaDebito": "45622",
                                        "cuentaCredito": "23678",
                                        "fecha": "2025-09-05"
                                      }
                                    ]
                                    """
                            )
                    )
            )
    })
    public List<TransferenciaDTO> getLastTransferencias() {
        return transferenciaUC.getLastTransferencias().stream().map(transferenciaMapper::transferenciaToDTO).toList();
    }

    @GetMapping("/by-cuit/{cuit:\\d+}")
    @Operation(
            summary = "Listar transferencias por cuit de empresa",
            description = """
                Devuelve una lista de transferencias segun un cuit de empresa
        
                ###CURL:
                ```bash
                curl -X GET "http://localhost:8080/transferencias/by-cuit/10" -H "accept: application/json"
                ```
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de transferencias dado un cuit ",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = """
                                    [
                                      {
                                        "id": 101,
                                        "importe": 1500,
                                        "empresa": "EmpresaA",
                                        "cuentaDebito": "1234",
                                        "cuentaCredito": "454564",
                                        "fecha": "2025-09-01"
                                      }
                                    ]
                                    """
                            )
                    )
            )
    })
    public List<TransferenciaDTO> getTransferenciasByCuit(@PathVariable Long cuit) {
        return transferenciaUC.getTransferenciasByCuit(cuit).stream().map(transferenciaMapper::transferenciaToDTO).toList();
    }
}
