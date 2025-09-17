package adapters.web;

import adapters.web.mappers.EmpresaMapper;
import domain.CuitException;
import domain.model.Empresa;
import domain.ports.in.EmpresaUC;
import dto.EmpresaDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "Operaciones relacionadas con empresas")
@ApiResponse(responseCode = "500", description = "Error interno del servidor")
public class EmpresaController {

    private EmpresaUC empresaUC;
    private EmpresaMapper empresaMapper;

    @Autowired
    public EmpresaController(EmpresaUC empresaUC, EmpresaMapper empresaMapper) {
        this.empresaUC = empresaUC;
        this.empresaMapper = empresaMapper;
    }

    @GetMapping
    @Operation(
            summary = "Listar todas las empresas",
            description = """
                Devuelve una lista de empresas registradas
        
                ###CURL:
                ```bash
                curl -X GET "http://localhost:8080/empresas" -H "accept: application/json"
                ```
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de empresas obtenido con éxito",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = """
                                    [
                                      {
                                        "id": 1,
                                        "cuit": 1234,
                                        "razonSocial": "EmpresaA",
                                        "fechaAdhesion": "2025-09-10"
                                      },
                                      {
                                        "id": 2,
                                        "cuit": 5678,
                                        "razonSocial": "EmpresaB",
                                        "fechaAdhesion": "2025-08-01"
                                      }
                                    ]
                                    """
                            )
                    )
            )
    })
    public List<EmpresaDTO> listEmpresas() {
        return empresaUC.listEmpresas().stream().
                map(empresaMapper::empresaToDTO)
                .toList();
    }

    @PostMapping
    @Operation(
            summary = "Agregar una nueva Empresa",
            description = "Agrega una nueva empresa al sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Creacion Empresa",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Empresa Nueva",
                                    value = """
                                    {
                                      "cuit": 1234,
                                      "razonSocial": "EmpresaA",
                                      "fechaAdhesion": "2025-09-10"
                                    }
                                    """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa creada"),
            @ApiResponse(responseCode = "409", description = "CUIT duplicado")
    })
    public ResponseEntity<EmpresaDTO> insertEmpresa(@RequestBody EmpresaDTO in_empresa) {
        try {
            Empresa empresa_save = empresaMapper.empresaToDomain(in_empresa);
            Empresa empresa_creada = empresaUC.insertEmpresa(empresa_save);
            return ResponseEntity.ok(empresaMapper.empresaToDTO(empresa_creada));
        }
        catch (CuitException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/last-month")
    @Operation(
            summary = "Listar empresas ultimo mes",
            description = """
                Devuelve una lista de empresas que se adherieron el ultimo mes
        
                ### CURL:
                ```bash
                curl -X GET "http://localhost:8080/empresas/last-month" -H "accept: application/json"
                ```
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de empresas que se adherieron el ultimo mes",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = """
                                    [
                                      {
                                        "id": 1,
                                        "cuit": 1234,
                                        "razonSocial": "EmpresaA",
                                        "fechaAdhesion": "2025-09-10"
                                      },
                                      {
                                        "id": 2,
                                        "cuit": 5678,
                                        "razonSocial": "EmpresaB",
                                        "fechaAdhesion": "2025-08-01"
                                      }
                                    ]
                                    """
                            )
                    )
            )
    })
    public List<EmpresaDTO> getEmpresasLastMonth() {
        return empresaUC.getEmpresasByLastMonth().stream().map(empresaMapper::empresaToDTO).toList();
    }

    @GetMapping("/by-id/{id}")
    @Operation(
            summary = "Listar empresas por cuit",
            description = """
                Devuelve empresas segun un cuit
        
                ###CURL:
                ```bash
                curl -X GET "http://localhost:8080/empresas/by-id/10" -H "accept: application/json"
                ```
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de empresas que tienen un cierto numero cuit",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = """
                                    [
                                      {
                                        "id": 1,
                                        "cuit": 1234,
                                        "razonSocial": "EmpresaA",
                                        "fechaAdhesion": "2025-09-10"
                                      }
                                    ]
                                    """
                            )
                    )
            )
    })
    public ResponseEntity<EmpresaDTO> searchByCuit(@PathVariable Long id) {
        return empresaUC.getEmpresasByCuit(id).map(empresaMapper::empresaToDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
