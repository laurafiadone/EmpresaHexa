package domain.ports.in;

import domain.model.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaUC {

    List<Empresa> listEmpresas();

    Optional<Empresa> getEmpresasByCuit(Long id);

    Empresa insertEmpresa(Empresa empresa);

    List<Empresa> getEmpresasByLastMonth();
}
