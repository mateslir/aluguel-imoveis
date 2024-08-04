package br.com.alugueimoveis.Fourcamp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {
    private int id;
    private int idImovel;
    private int idCliente;
    private int nota;
    private String comentarios;

}
