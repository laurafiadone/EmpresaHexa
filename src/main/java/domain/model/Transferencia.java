package domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Transferencia {

    private int importe;
    private Long empresa;
    private String cuentaDebito, cuentaCredito;
    private LocalDate fecha;


    public Transferencia(Transferencia transferencia) {
        this.importe = transferencia.getImporte();
        this.empresa = transferencia.getEmpresa();
        this.cuentaDebito = transferencia.getCuentaDebito();
        this.cuentaCredito = transferencia.getCuentaCredito();
        this.fecha = transferencia.getFecha();
    }

    public Transferencia(int importe, Long empresa, String cuentaDebito, String cuentaCredito, LocalDate fecha) {
        this.importe = importe;
        this.empresa = empresa;
        this.cuentaCredito = cuentaCredito;
        this.cuentaDebito = cuentaDebito;
        this.fecha = fecha;
    }
}
