package adapters.persistence.mappers;

import domain.model.Transferencia;
import infrastructure.entity.TransferenciaEntity;
import org.springframework.stereotype.Component;

@Component
public class TransferenciaEntityMapper {

    public Transferencia transferenciaToDomain(TransferenciaEntity entity) {
        return new Transferencia(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha());
    }

    public TransferenciaEntity transferenciaToEntity(Transferencia transferencia) {
        TransferenciaEntity entity = new TransferenciaEntity();
        entity.setImporte(transferencia.getImporte());
        entity.setEmpresa(transferencia.getEmpresa());
        entity.setCuentaDebito(transferencia.getCuentaDebito());
        entity.setCuentaCredito(transferencia.getCuentaCredito());
        entity.setFecha(transferencia.getFecha());
        return entity;
    }
}
