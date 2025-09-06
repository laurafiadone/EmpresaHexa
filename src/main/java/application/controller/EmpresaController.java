package application.controller;

import domain.model.Empresa;
import domain.ports.in.EmpresaUC;
import dto.EmpresaDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "Operaciones relacionadas con empresas")
public class EmpresaController {

    private EmpresaUC empresaUC;

    @Autowired
    public EmpresaController(EmpresaUC empresaUC) {
        this.empresaUC = empresaUC;
    }

    @GetMapping
    @Operation(summary = "Listar todas las empresas", description = "Devuelve una lista de empresas registradas")
    public List<EmpresaDTO> listEmpresas() {
        return empresaUC.listEmpresas().stream().map(dto -> new EmpresaDTO(dto.getCuit(), dto.getRazon_social(), dto.getFechaAdhesion())).toList();
    }

    @PostMapping
    /*Insert New Empresa*/
    @Operation(summary = "Agregar una Empresa", description = "Agrega una nueva empresa al sistema")
    public ResponseEntity<EmpresaDTO> insertEmpresa(@RequestBody EmpresaDTO in_empresa) {
        Empresa empresa_save = new Empresa(in_empresa.getCuit(), in_empresa.getRazon_social(), in_empresa.getFechaAdhesion());
        Empresa empresa_creada = empresaUC.insertEmpresa(empresa_save);
        EmpresaDTO empresaDTO = new EmpresaDTO(empresa_creada.getCuit(), empresa_creada.getRazon_social(), empresa_creada.getFechaAdhesion());
        return ResponseEntity.ok(empresaDTO);
    }

    @GetMapping("/last-month")
    /*empresas que hicieron transferencias el último mes*/
    @Operation(summary = "Listar empresas ultimo mes", description = "Devuelve una lista de empresas que se adherieron el ultimo mes")
    public List<EmpresaDTO> getEmpresasLastMonth() {
        return empresaUC.getEmpresasByLastMonth().stream().map(dto -> new EmpresaDTO(dto.getCuit(), dto.getRazon_social(), dto.getFechaAdhesion())).toList();
    }

    @GetMapping("/by-id/{id}")
    @Operation(summary = "Listar empresas por cuit", description = "Devuelve empresas segun un cuit")
    public ResponseEntity<EmpresaDTO> searchByCuit(@PathVariable Long id) {
        return empresaUC.getEmpresasByCuit(id).map(entity -> new EmpresaDTO(entity.getCuit(), entity.getRazon_social(), entity.getFechaAdhesion())).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
