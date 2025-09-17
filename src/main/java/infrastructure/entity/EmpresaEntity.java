package infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "empresa")
public class EmpresaEntity {
    @Id
    @Column(name = "cuit")
    private Long cuit;
    @Column(name = "razon_social")
    private String razonSocial;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_adhesion")
    private LocalDate fechaAdhesion;
}
