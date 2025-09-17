package application;

import domain.model.Transferencia;
import domain.ports.in.TransferenciaUC;
import domain.ports.out.TransferenciaRepoPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferenciaUseCaseImpl implements TransferenciaUC {

    private TransferenciaRepoPort transferenciaRepoPort;

    public TransferenciaUseCaseImpl(TransferenciaRepoPort transferenciaRepoPort) {
        this.transferenciaRepoPort = transferenciaRepoPort;
    }

    @Override
    public List<Transferencia> getTransferencias() {
        return transferenciaRepoPort.findAll();
    }

    @Override
    public List<Transferencia> getTransferenciasByCuit(Long cuit) {
        return transferenciaRepoPort.findByEmpresaCuit(cuit);
    }

    @Override
    public Transferencia insertTransferencia(Transferencia transferencia) {
        return transferenciaRepoPort.save(transferencia);
    }

    @Override
    public List<Transferencia> getLastTransferencias() {
        return transferenciaRepoPort.getTransferenciasByLastPeriod();
    }
}
