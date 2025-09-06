package application.controller;

import domain.model.Transferencia;
import domain.ports.in.TransferenciaUC;
import dto.TransferenciaDTO;
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

    @Autowired
    public TransferenciaController(TransferenciaUC transferenciaUC) {
        this.transferenciaUC = transferenciaUC;
    }

    @GetMapping
    @Operation(summary = "Listar todas las Transferencias", description = "Devuelve una lista de transferencias registradas")
    public List<TransferenciaDTO> getTransferencias() {
        return transferenciaUC.getTransferencias().stream().map(entity -> new TransferenciaDTO(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha())).toList();
    }

    @PostMapping
    @Operation(summary = "Agregar una nueva transferencia", description = "Agrega al sistema una nueva transferencia")
    public ResponseEntity<TransferenciaDTO> insertTransferencia(@RequestBody TransferenciaDTO in_Transferencia) {
        Transferencia transferencia_save = new Transferencia(in_Transferencia.getImporte(), in_Transferencia.getEmpresa(), in_Transferencia.getCuentaDebito(), in_Transferencia.getCuentaCredito(), in_Transferencia.getFecha());
        Transferencia transferencia_creada = transferenciaUC.insertTransferencia(transferencia_save);
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia_creada.getImporte(), transferencia_creada.getEmpresa(), transferencia_creada.getCuentaDebito(), transferencia_creada.getCuentaCredito(), transferencia_creada.getFecha());

        return ResponseEntity.ok(transferenciaDTO);
    }

    @GetMapping("/period/last-transferencias")
    /*Empresas que hicieron transferencias el ultimo mes*/
    @Operation(summary = "Obtener Empresas y Transferencias", description = "Devuelve una lista las ultimas empresas que hicieron transferencias")
    public List<TransferenciaDTO> getLastTransferencias() {
        return transferenciaUC.getLastTransferencias().stream().map(entity -> new TransferenciaDTO(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha())).toList();
    }

    @GetMapping("/by-cuit/{cuit:\\d+}")
    @Operation(summary = "Listar transferencias por cuit de empresa", description = "Devuelve una lista de transferencias segun un cuit de empresa")
    public List<TransferenciaDTO> getTransferenciasByCuit(@PathVariable Long cuit) {
        return transferenciaUC.getTransferenciasByCuit(cuit).stream().map(entity -> new TransferenciaDTO(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha())).toList();
    }
}
