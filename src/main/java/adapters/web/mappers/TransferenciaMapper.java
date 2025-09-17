package adapters.web.mappers;

import domain.model.Transferencia;
import dto.TransferenciaDTO;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaMapper {

    public TransferenciaDTO transferenciaToDTO(Transferencia transferencia) {
        return new TransferenciaDTO(transferencia.getImporte(), transferencia.getEmpresa(), transferencia.getCuentaDebito(), transferencia.getCuentaCredito(), transferencia.getFecha());
    }

    public Transferencia transferenciaToDomain(TransferenciaDTO transferenciaDTO) {
        return new Transferencia(transferenciaDTO.getImporte(), transferenciaDTO.getEmpresa(), transferenciaDTO.getCuentaDebito(), transferenciaDTO.getCuentaCredito(), transferenciaDTO.getFecha());
    }
}
