package br.com.alugueimoveis.Fourcamp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Imovel {
    private Long id;
    private String tipoImovel;
    private String cepImovel;
    private String enderecoImovel;
    private String estadoImovel;
    private String cidadeImovel;
    private String bairroImovel;
    private Double areaImovel;
    private String tituloImovel;
    private String descricaoImovel;
    private Double preco;
    private Long clienteId;
    // Getters e Setters
    //...
}



