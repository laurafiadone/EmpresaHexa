package application.adaptador;

import domain.model.Empresa;
import domain.ports.out.EmpresaRepoPort;
import infrastructure.EmpresaEntity;
import infrastructure.SpringDataEmpresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class EmpresaAdapter implements EmpresaRepoPort {

    private SpringDataEmpresaRepo jpaEmpresa;

    @Autowired
    public EmpresaAdapter(SpringDataEmpresaRepo jpaEmpresa) {
        this.jpaEmpresa = jpaEmpresa;
    }

    @Override
    public List<Empresa> findAll() {
        return jpaEmpresa.findAll().stream().map(entity -> new Empresa(entity.getCuit(), entity.getRazon_social(), entity.getFechaAdhesion())).toList();
    }

    @Override
    public Optional<Empresa> findByCuit(Long cuit) {
        return jpaEmpresa.findById(cuit).map(entity -> new Empresa(entity.getCuit(), entity.getRazon_social(), entity.getFechaAdhesion()));
    }

    @Override
    public Empresa save(Empresa empresa) {
        //se puede modularizar
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setFechaAdhesion(empresa.getFechaAdhesion());
        empresaEntity.setRazon_social(empresa.getRazon_social());

        EmpresaEntity empresaToSave = jpaEmpresa.save(empresaEntity);

        return new Empresa(empresaToSave.getCuit(), empresaToSave.getRazon_social(), empresaToSave.getFechaAdhesion());
    }

    @Override
    /*empresas que se adhirieron el último mes.*/
    public List<Empresa> findByFechaAdhesionBetween() {
        LocalDate today = LocalDate.now();
        return jpaEmpresa.findByFechaAdhesionBetween(today.minusMonths(1), today).stream().map(entity -> new Empresa(entity.getCuit(), entity.getRazon_social(), entity.getFechaAdhesion())).toList();
    }
}
