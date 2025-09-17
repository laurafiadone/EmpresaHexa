package application;

import domain.CuitException;
import domain.model.Empresa;
import domain.ports.in.EmpresaUC;
import domain.ports.out.EmpresaRepoPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaUseCaseImpl implements EmpresaUC {

    private EmpresaRepoPort empresaRepoPort;

    public EmpresaUseCaseImpl(EmpresaRepoPort empresaRepoPort) {
        this.empresaRepoPort = empresaRepoPort;
    }

    @Override
    public List<Empresa> listEmpresas() {
        return empresaRepoPort.findAll();
    }

    @Override
    public Optional<Empresa> getEmpresasByCuit(Long id) {
        return empresaRepoPort.findByCuit(id);
    }

    @Override
    public Empresa insertEmpresa(Empresa empresa) {
        Optional<Empresa> empresaPresent = empresaRepoPort.findByCuit(empresa.getCuit());
        if (empresaPresent.isPresent()) {
            throw new CuitException("Cuit already exists" + empresa.getCuit());
        }
        return empresaRepoPort.save(empresa);
    }

    @Override
    public List<Empresa> getEmpresasByLastMonth() {
        return empresaRepoPort.findByFechaAdhesionBetween();
    }
}
