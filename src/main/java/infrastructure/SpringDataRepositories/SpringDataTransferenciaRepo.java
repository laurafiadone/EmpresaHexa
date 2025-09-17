package infrastructure.SpringDataRepositories;

import infrastructure.entity.TransferenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataTransferenciaRepo extends JpaRepository<TransferenciaEntity, Long> {
    List<TransferenciaEntity> findByEmpresa(Long empresa);

    List<TransferenciaEntity> findByFechaBetween(LocalDate dateFrom, LocalDate dateTo);
}
