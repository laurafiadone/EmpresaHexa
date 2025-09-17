package infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
    private Long cuit;// set as auto increment in DB
    @Column(name = "razon_social")
    private String razonSocial;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_adhesion")
    private LocalDate fechaAdhesion;
}
