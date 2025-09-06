package domain.service;

import domain.model.Empresa;
import domain.ports.in.EmpresaUC;
import domain.ports.out.EmpresaRepoPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements EmpresaUC {

    private EmpresaRepoPort empresaRepoPort;

    public EmpresaService(EmpresaRepoPort empresaRepoPort) {
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
        return empresaRepoPort.save(empresa);
    }

    @Override
    public List<Empresa> getEmpresasByLastMonth() {
        return empresaRepoPort.findByFechaAdhesionBetween();
    }
}
