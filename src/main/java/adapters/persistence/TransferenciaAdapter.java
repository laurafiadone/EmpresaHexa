package adapters.persistence;

import adapters.persistence.mappers.TransferenciaEntityMapper;
import domain.model.Transferencia;
import domain.ports.out.TransferenciaRepoPort;
import infrastructure.SpringDataRepositories.SpringDataTransferenciaRepo;
import infrastructure.entity.TransferenciaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TransferenciaAdapter implements TransferenciaRepoPort {

    private SpringDataTransferenciaRepo jpaTransferencia;
    private TransferenciaEntityMapper entityMapper;
    private final Clock clock;

    @Autowired
    public TransferenciaAdapter(SpringDataTransferenciaRepo jpaTransferencia, TransferenciaEntityMapper entityMapper, Clock clock) {
        this.jpaTransferencia = jpaTransferencia;
        this.entityMapper = entityMapper;
        this.clock = clock;
    }

    @Override
    public List<Transferencia> findAll() {
        return jpaTransferencia.findAll().stream().map(entityMapper::transferenciaToDomain).toList();
    }

    @Override
    public List<Transferencia> findByEmpresaCuit(Long cuit) {
        return jpaTransferencia.findByEmpresa(cuit).stream().map(entityMapper::transferenciaToDomain).toList();
    }

    @Override
    public Transferencia save(Transferencia transferencia) {
        TransferenciaEntity transferenciaEntity = entityMapper.transferenciaToEntity(transferencia);
        return entityMapper.transferenciaToDomain(jpaTransferencia.save(transferenciaEntity));
    }

    @Override
    /*empresas que hicieron transferencias el último mes*/
    public List<Transferencia> getTransferenciasByLastPeriod() {
        LocalDate today = LocalDate.now(clock);
        return jpaTransferencia.findByFechaBetween(today.minusMonths(1), today).stream().map(entityMapper::transferenciaToDomain).toList();
    }
}
