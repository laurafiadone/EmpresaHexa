package domain.ports.out;

import domain.model.Transferencia;

import java.util.List;

public interface TransferenciaRepoPort {
    List<Transferencia> findAll();

    List<Transferencia> findByEmpresaCuit(Long cuit);

    Transferencia save(Transferencia transferencia);

    List<Transferencia> getTransferenciasByLastPeriod();
}
