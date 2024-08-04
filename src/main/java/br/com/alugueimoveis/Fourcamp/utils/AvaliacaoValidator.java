package br.com.alugueimoveis.Fourcamp.utils;



import br.com.alugueimoveis.Fourcamp.dao.AvaliacaoRepository;
import br.com.alugueimoveis.Fourcamp.dao.ClienteRepository;
import br.com.alugueimoveis.Fourcamp.dao.ImovelRepository;
import br.com.alugueimoveis.Fourcamp.dao.ReservaRepository;
import br.com.alugueimoveis.Fourcamp.model.Avaliacao;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoValidator {

    private final ImovelRepository imovelRepository;
    private final ClienteRepository clienteRepository;

    public AvaliacaoValidator(ImovelRepository imovelRepository, ClienteRepository clienteRepository) {
        this.imovelRepository = imovelRepository;
        this.clienteRepository = clienteRepository;
    }
    public void validarAvaliacao(Avaliacao avaliacao) throws IllegalArgumentException {
        StringBuilder erros = new StringBuilder();

        if (!validarIdImovel(avaliacao.getIdImovel())) {
            erros.append("Id do imóvel não existe. ");
        }
        if (!validarIdCliente(avaliacao.getIdCliente())) {
            erros.append("Id do cliente não existe. ");
        }
        if (AvaliacaoRepository.existsByIdClienteAndIdImovel(avaliacao.getIdImovel(), avaliacao.getIdCliente())) {
            erros.append("Cliente já avaliou o mesmo imóvel. ");
        }
        if (avaliacao.getNota() < 1 || avaliacao.getNota() > 5) {
            erros.append("Nota deve estar entre 1 e 5. ");
        }
        if (avaliacao.getComentarios() == null || avaliacao.getComentarios().trim().isEmpty()) {
            erros.append("Comentários não podem estar vazios. ");
        }
        if (erros.length() > 0) {
            throw new IllegalArgumentException(erros.toString().trim());
        }
    }

    public boolean validarIdImovel(int idImovel) {
        return imovelRepository.existsById(idImovel);
    }

    public boolean validarIdCliente(int idCliente) {
        return clienteRepository.existsById(idCliente);
    }
}