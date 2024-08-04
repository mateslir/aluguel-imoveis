package br.com.alugueimoveis.Fourcamp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    private int id;
    private int idCliente;
    private int idImovel;
    private String dataInicio;
    private String dataFim;
    private String status;

}
