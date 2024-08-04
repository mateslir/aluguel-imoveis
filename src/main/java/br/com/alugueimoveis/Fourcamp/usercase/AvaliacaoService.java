package br.com.alugueimoveis.Fourcamp.usercase;

import br.com.alugueimoveis.Fourcamp.dao.AvaliacaoRepository;
import br.com.alugueimoveis.Fourcamp.dtos.AvaliacaoDTO;
import br.com.alugueimoveis.Fourcamp.model.Avaliacao;
import br.com.alugueimoveis.Fourcamp.utils.AvaliacaoValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoValidator avaliacaoValidator;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, AvaliacaoValidator avaliacaoValidator) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoValidator = avaliacaoValidator;
    }

    public void criarAvaliacao(Avaliacao avaliacao) {
        try {
            avaliacaoValidator.validarAvaliacao(avaliacao);
            avaliacaoRepository.save(avaliacao);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    public List<AvaliacaoDTO> obterAvaliacoesPorIdImovel(int idImovel) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByIdImovel(idImovel);
        return avaliacoes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AvaliacaoDTO mapToDTO(Avaliacao avaliacao) {
        AvaliacaoDTO dto = new AvaliacaoDTO();
        dto.setIdCliente(avaliacao.getIdCliente());
        dto.setNota(avaliacao.getNota());
        dto.setComentarios(avaliacao.getComentarios());
        return dto;
    }

    public void atualizarAvaliacao(Avaliacao avaliacao) {
        Avaliacao existente = avaliacaoRepository.findById(avaliacao.getId());
        if (existente == null) {
            throw new IllegalArgumentException("Avaliação não encontrada.");
        }
        avaliacaoValidator.validarAvaliacao(avaliacao);
        avaliacaoRepository.updateAvaliacao(avaliacao);
    }
    public void deletarAvaliacao(int id) {
        Avaliacao existente = avaliacaoRepository.findById(id);
        if (existente == null) {
            throw new IllegalArgumentException("Avaliação não encontrada.");
        }
        avaliacaoRepository.deleteAvaliacao(id);
    }
    public List<Avaliacao> obterTodasAvaliacoes() {
        return avaliacaoRepository.findAll();
    }
}