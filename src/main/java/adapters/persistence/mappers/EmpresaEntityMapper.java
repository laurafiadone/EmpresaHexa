package adapters.persistence.mappers;

import domain.model.Empresa;
import infrastructure.entity.EmpresaEntity;
import org.springframework.stereotype.Component;

@Component
public class EmpresaEntityMapper {

    public Empresa empresaToDomain(EmpresaEntity empresaEntity) {
        return new Empresa(empresaEntity.getCuit(), empresaEntity.getRazonSocial(), empresaEntity.getFechaAdhesion());
    }

    public EmpresaEntity empresaToEntity(Empresa empresa) {
        EmpresaEntity empresaEntity = new EmpresaEntity();
        empresaEntity.setCuit(empresa.getCuit());
        empresaEntity.setRazonSocial(empresa.getRazon_social());
        empresaEntity.setFechaAdhesion(empresa.getFechaAdhesion());
        return empresaEntity;
    }
}
