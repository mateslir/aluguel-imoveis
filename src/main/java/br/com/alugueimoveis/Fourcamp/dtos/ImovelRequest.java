package br.com.alugueimoveis.Fourcamp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImovelRequest {
    private String tipoImovel;
    private String cepImovel;
    private String enderecoImovel;
    private String estadoImovel;
    private String cidadeImovel;
    private String bairroImovel;
    private String areaImovel;
    private String tituloImovel;
    private String descricaoImovel;
    private String preco;
    private Long clienteId;
}
