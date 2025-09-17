package adapters.persistence;

import adapters.persistence.mappers.EmpresaEntityMapper;
import domain.CuitException;
import domain.model.Empresa;
import domain.ports.out.EmpresaRepoPort;
import infrastructure.SpringDataRepositories.SpringDataEmpresaRepo;
import infrastructure.entity.EmpresaEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class EmpresaAdapter implements EmpresaRepoPort {

    @Autowired
    private EntityManager em;
    private SpringDataEmpresaRepo jpaEmpresa;
    private EmpresaEntityMapper entityMapper;
    private final Clock clock;

    @Autowired
    public EmpresaAdapter(SpringDataEmpresaRepo jpaEmpresa, EmpresaEntityMapper entityMapper, Clock clock) {
        this.jpaEmpresa = jpaEmpresa;
        this.entityMapper = entityMapper;
        this.clock = clock;
    }

    @Override
    public List<Empresa> findAll() {
        return jpaEmpresa.findAll().stream().map(entityMapper::empresaToDomain).toList();
    }

    @Override
    public Optional<Empresa> findByCuit(Long cuit) {
        return jpaEmpresa.findById(cuit).map(entityMapper::empresaToDomain);
    }

    @Override
    public Empresa save(Empresa empresa) {
        if (jpaEmpresa.existsById(empresa.getCuit())) {
            throw new CuitException("Cuit already exists: " + empresa.getCuit());
        }
        EmpresaEntity empresaEntity = entityMapper.empresaToEntity(empresa);
        EmpresaEntity saved = jpaEmpresa.save(empresaEntity);
        return entityMapper.empresaToDomain(saved);
    }

    @Override
    /*empresas que se adhirieron el último mes.*/
    public List<Empresa> findByFechaAdhesionBetween() {
        LocalDate today = LocalDate.now(clock);
        return jpaEmpresa.findByFechaAdhesionBetween(today.minusMonths(1), today).stream().map(entityMapper::empresaToDomain).toList();
    }
}
