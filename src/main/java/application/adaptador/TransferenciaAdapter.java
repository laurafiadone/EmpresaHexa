package application.adaptador;

import domain.model.Transferencia;
import domain.ports.out.TransferenciaRepoPort;
import infrastructure.SpringDataTransferenciaRepo;
import infrastructure.TransferenciaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TransferenciaAdapter implements TransferenciaRepoPort {

    private SpringDataTransferenciaRepo jpaTransferencia;

    @Autowired
    public TransferenciaAdapter(SpringDataTransferenciaRepo jpaTransferencia) {
        this.jpaTransferencia = jpaTransferencia;
    }

    @Override
    public List<Transferencia> findAll() {
        return jpaTransferencia.findAll().stream().map(entity -> new Transferencia(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha())).toList();
    }

    @Override
    public List<Transferencia> findByEmpresaCuit(Long cuit) {
        return jpaTransferencia.findByEmpresa(cuit).stream().map(entity -> new Transferencia(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), entity.getFecha())).toList();
    }

    @Override
    public Transferencia save(Transferencia transferencia) {
        //se puede modularizar
        TransferenciaEntity transferenciaEntity = new TransferenciaEntity();

        transferenciaEntity.setImporte(transferencia.getImporte());
        transferenciaEntity.setEmpresa(transferencia.getEmpresa());
        transferenciaEntity.setCuentaDebito(transferencia.getCuentaDebito());
        transferenciaEntity.setCuentaCredito(transferencia.getCuentaCredito());

        TransferenciaEntity transferenciaToSave = jpaTransferencia.save(transferenciaEntity);

        return new Transferencia(transferenciaToSave.getImporte(), transferenciaToSave.getEmpresa(), transferenciaToSave.getCuentaDebito(), transferenciaToSave.getCuentaCredito(), transferenciaToSave.getFecha());
    }

    @Override
    /*empresas que hicieron transferencias el último mes*/
    public List<Transferencia> getTransferenciasByLastPeriod() {
        LocalDate today = LocalDate.now();
        return jpaTransferencia.findByFechaBetween(today.minusMonths(1), today).stream().map(entity -> new Transferencia(entity.getImporte(), entity.getEmpresa(), entity.getCuentaDebito(), entity.getCuentaCredito(), null)).toList();
    }
}
