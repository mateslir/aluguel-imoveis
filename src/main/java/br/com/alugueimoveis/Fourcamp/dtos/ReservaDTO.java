package br.com.alugueimoveis.Fourcamp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private int idImovel;
    private int idCliente;
    private String dataInicio;
    private String dataFim;
    private String status;

}
