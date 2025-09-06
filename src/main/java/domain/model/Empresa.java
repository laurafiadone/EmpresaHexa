package domain.model;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Empresa {

    private Long cuit;
    private String razon_social;
    @Column(name = "fecha_adhesion")
    private LocalDate fechaAdhesion;

    public Empresa(Empresa empresa) {
        this.cuit = empresa.getCuit();
        this.razon_social = empresa.getRazon_social();
        this.fechaAdhesion = empresa.getFechaAdhesion();
    }

    public Empresa(Long cuit, String razon_social, LocalDate fecha) {
        this.cuit = cuit;
        this.razon_social = razon_social;
        this.fechaAdhesion = fecha;
    }
}
