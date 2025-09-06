package domain.ports.in;

import domain.model.Transferencia;

import java.util.List;

public interface TransferenciaUC {
    List<Transferencia> getTransferencias();

    List<Transferencia> getTransferenciasByCuit(Long cuit);

    Transferencia insertTransferencia(Transferencia transferencia);

    List<Transferencia> getLastTransferencias();
}
