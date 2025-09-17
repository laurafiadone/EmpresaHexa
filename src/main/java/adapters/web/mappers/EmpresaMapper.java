package adapters.web.mappers;

import domain.model.Empresa;
import dto.EmpresaDTO;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {

    public EmpresaDTO empresaToDTO(Empresa empresa) {
        return new EmpresaDTO(empresa.getCuit(), empresa.getRazon_social(), empresa.getFechaAdhesion());
    }

    public Empresa empresaToDomain(EmpresaDTO empresaDTO) {
        return new Empresa(empresaDTO.getCuit(), empresaDTO.getRazon_social(), empresaDTO.getFechaAdhesion());
    }
}
