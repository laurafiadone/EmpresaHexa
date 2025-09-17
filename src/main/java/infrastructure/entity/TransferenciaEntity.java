package infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Transferencia")
public class TransferenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long empresa;
    private int importe;
    @Column(name = "cuentaDebito")
    private String cuentaDebito;
    @Column(name = "cuentaCredito")
    private String cuentaCredito;
    @Column(name = "fecha")
    private LocalDate fecha;
}
