package domain.ports.out;

import domain.model.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepoPort {
    List<Empresa> findAll();

    Optional<Empresa> findByCuit(Long cuit);

    Empresa save(Empresa empresa);

    List<Empresa> findByFechaAdhesionBetween();
}
