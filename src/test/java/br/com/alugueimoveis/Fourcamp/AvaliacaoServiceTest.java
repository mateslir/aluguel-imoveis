package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.dao.AvaliacaoRepository;
import br.com.alugueimoveis.Fourcamp.dtos.AvaliacaoDTO;
import br.com.alugueimoveis.Fourcamp.model.Avaliacao;
import br.com.alugueimoveis.Fourcamp.usercase.AvaliacaoService;
import br.com.alugueimoveis.Fourcamp.utils.AvaliacaoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private AvaliacaoValidator avaliacaoValidator;

    private Avaliacao avaliacao;

    @BeforeEach
    public void setUp() {
        avaliacao = new Avaliacao();
        avaliacao.setId(1);
        avaliacao.setIdCliente(1);
        avaliacao.setNota(5);
        avaliacao.setComentarios("Excelente!");
    }

    @Test
    public void criarAvaliacao_sucesso() {
        doNothing().when(avaliacaoValidator).validarAvaliacao(avaliacao);
        doNothing().when(avaliacaoRepository).save(avaliacao);

        avaliacaoService.criarAvaliacao(avaliacao);

        verify(avaliacaoValidator, times(1)).validarAvaliacao(avaliacao);
        verify(avaliacaoRepository, times(1)).save(avaliacao);
    }

    @Test
    public void criarAvaliacao_falha() {
        doThrow(new IllegalArgumentException("Erro de validação")).when(avaliacaoValidator).validarAvaliacao(avaliacao);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> avaliacaoService.criarAvaliacao(avaliacao));

        assertEquals("Erro de validação", exception.getMessage());
        verify(avaliacaoValidator, times(1)).validarAvaliacao(avaliacao);
        verify(avaliacaoRepository, never()).save(avaliacao);
    }

    @Test
    public void obterAvaliacoesPorIdImovel_sucesso() {
        List<Avaliacao> avaliacoes = Arrays.asList(avaliacao);
        when(avaliacaoRepository.findByIdImovel(1)).thenReturn(avaliacoes);

        List<AvaliacaoDTO> avaliacaoDTOS = avaliacaoService.obterAvaliacoesPorIdImovel(1);

        assertEquals(1, avaliacaoDTOS.size());
        assertEquals(avaliacao.getNota(), avaliacaoDTOS.get(0).getNota());
        verify(avaliacaoRepository, times(1)).findByIdImovel(1);
    }

    @Test
    public void atualizarAvaliacao_sucesso() {
        when(avaliacaoRepository.findById(avaliacao.getId())).thenReturn(avaliacao);
        doNothing().when(avaliacaoValidator).validarAvaliacao(avaliacao);
        doNothing().when(avaliacaoRepository).updateAvaliacao(avaliacao);

        avaliacaoService.atualizarAvaliacao(avaliacao);

        verify(avaliacaoRepository, times(1)).findById(avaliacao.getId());
        verify(avaliacaoValidator, times(1)).validarAvaliacao(avaliacao);
        verify(avaliacaoRepository, times(1)).updateAvaliacao(avaliacao);
    }

    @Test
    public void atualizarAvaliacao_falha() {
        when(avaliacaoRepository.findById(avaliacao.getId())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> avaliacaoService.atualizarAvaliacao(avaliacao));

        assertEquals("Avaliação não encontrada.", exception.getMessage());
        verify(avaliacaoRepository, times(1)).findById(avaliacao.getId());
        verify(avaliacaoValidator, never()).validarAvaliacao(avaliacao);
        verify(avaliacaoRepository, never()).updateAvaliacao(avaliacao);
    }

    @Test
    public void deletarAvaliacao_sucesso() {
        when(avaliacaoRepository.findById(avaliacao.getId())).thenReturn(avaliacao);
        doNothing().when(avaliacaoRepository).deleteAvaliacao(avaliacao.getId());

        avaliacaoService.deletarAvaliacao(avaliacao.getId());

        verify(avaliacaoRepository, times(1)).findById(avaliacao.getId());
        verify(avaliacaoRepository, times(1)).deleteAvaliacao(avaliacao.getId());
    }

    @Test
    public void deletarAvaliacao_falha() {
        when(avaliacaoRepository.findById(avaliacao.getId())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> avaliacaoService.deletarAvaliacao(avaliacao.getId()));

        assertEquals("Avaliação não encontrada.", exception.getMessage());
        verify(avaliacaoRepository, times(1)).findById(avaliacao.getId());
        verify(avaliacaoRepository, never()).deleteAvaliacao(avaliacao.getId());
    }

    @Test
    public void obterTodasAvaliacoes_sucesso() {
        List<Avaliacao> avaliacoes = Arrays.asList(avaliacao);
        when(avaliacaoRepository.findAll()).thenReturn(avaliacoes);

        List<Avaliacao> result = avaliacaoService.obterTodasAvaliacoes();

        assertEquals(1, result.size());
        verify(avaliacaoRepository, times(1)).findAll();
    }
}
