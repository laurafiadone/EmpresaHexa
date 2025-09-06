package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import domain.model.Transferencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaDTO {
    @JsonProperty("importe")
    private int importe;
    @JsonProperty("empresa")
    private Long empresa;
    @JsonProperty("cuentaDebito")
    private String cuentaDebito;
    @JsonProperty("cuentaCredito")
    private String cuentaCredito;
    @JsonProperty("fecha")
    private LocalDate fecha;

    public TransferenciaDTO(Transferencia transferencia) {
        this.importe = transferencia.getImporte();
        this.empresa = transferencia.getEmpresa();
        this.cuentaDebito = transferencia.getCuentaDebito();
        this.cuentaCredito = transferencia.getCuentaCredito();
        this.fecha = transferencia.getFecha();
    }
}
