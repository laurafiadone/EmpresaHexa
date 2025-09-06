package application.controller;

import domain.model.Transferencia;
import domain.ports.in.TransferenciaUC;
import dto.TransferenciaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    private TransferenciaUC transferenciaUC;

    @Autowired
    public TransferenciaController(TransferenciaUC transferenciaUC) {
        this.transferenciaUC = transferenciaUC;
    }

    @GetMapping
    public List<TransferenciaDTO> getTransferencias() {
        return transferenciaUC.getTransferencias().stream().map(entity -> new TransferenciaDTO(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha())).toList();
    }

    @PostMapping
    public ResponseEntity<TransferenciaDTO> insertTransferencia(@RequestBody TransferenciaDTO in_Transferencia) {
        Transferencia transferencia_save = new Transferencia(in_Transferencia.getImporte(), in_Transferencia.getEmpresa(), in_Transferencia.getCuentaDebito(), in_Transferencia.getCuentaCredito(), in_Transferencia.getFecha());
        Transferencia transferencia_creada = transferenciaUC.insertTransferencia(transferencia_save);
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(transferencia_creada.getImporte(), transferencia_creada.getEmpresa(), transferencia_creada.getCuentaDebito(), transferencia_creada.getCuentaCredito(), transferencia_creada.getFecha());

        return ResponseEntity.ok(transferenciaDTO);
    }

    @GetMapping("/period/last-transferencias")
    /*Empresas que hicieron transferencias el ultimo mes*/
    public List<TransferenciaDTO> getLastTransferencias() {
        return transferenciaUC.getLastTransferencias().stream().map(entity -> new TransferenciaDTO(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha())).toList();
    }

    @GetMapping("/by-cuit/{cuit:\\d+}")
    public List<TransferenciaDTO> getTransferenciasByCuit(@PathVariable Long cuit) {
        return transferenciaUC.getTransferenciasByCuit(cuit).stream().map(entity -> new TransferenciaDTO(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha())).toList();
    }
}
