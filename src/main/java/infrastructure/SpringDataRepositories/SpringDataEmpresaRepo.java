package infrastructure.SpringDataRepositories;

import infrastructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataEmpresaRepo extends JpaRepository<EmpresaEntity, Long> {
    List<EmpresaEntity> findByFechaAdhesionBetween(LocalDate dateFrom, LocalDate dateTo);
}
