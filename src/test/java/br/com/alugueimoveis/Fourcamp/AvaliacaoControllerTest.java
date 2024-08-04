package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.controller.AvaliacaoController;
import br.com.alugueimoveis.Fourcamp.dtos.AvaliacaoDTO;
import br.com.alugueimoveis.Fourcamp.model.Avaliacao;
import br.com.alugueimoveis.Fourcamp.usercase.AvaliacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AvaliacaoControllerTest {

    @InjectMocks
    private AvaliacaoController avaliacaoController;

    @Mock
    private AvaliacaoService avaliacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarAvaliacao() {
        Avaliacao avaliacao = new Avaliacao();
        doNothing().when(avaliacaoService).criarAvaliacao(avaliacao);

        ResponseEntity<String> response = avaliacaoController.criarAvaliacao(avaliacao);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Avaliação criada com sucesso", response.getBody());
    }

    @Test
    public void testCriarAvaliacaoBadRequest() {
        Avaliacao avaliacao = new Avaliacao();
        doThrow(new IllegalArgumentException("Invalid input")).when(avaliacaoService).criarAvaliacao(avaliacao);

        ResponseEntity<String> response = avaliacaoController.criarAvaliacao(avaliacao);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao criar avaliação: Invalid input", response.getBody());
    }

    @Test
    public void testObterTodasAvaliacoes() {
        List<Avaliacao> avaliacoes = List.of(new Avaliacao());
        when(avaliacaoService.obterTodasAvaliacoes()).thenReturn(avaliacoes);

        ResponseEntity<List<Avaliacao>> response = avaliacaoController.obterTodasAvaliacoes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacoes, response.getBody());
    }

    @Test
    public void testObterTodasAvaliacoesNoContent() {
        when(avaliacaoService.obterTodasAvaliacoes()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Avaliacao>> response = avaliacaoController.obterTodasAvaliacoes();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testObterAvaliacoesPorIdImovel() {
        int idImovel = 1;
        List<AvaliacaoDTO> avaliacoesDTO = List.of(new AvaliacaoDTO());
        when(avaliacaoService.obterAvaliacoesPorIdImovel(idImovel)).thenReturn(avaliacoesDTO);

        ResponseEntity<List<AvaliacaoDTO>> response = avaliacaoController.obterAvaliacoesPorIdImovel(idImovel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(avaliacoesDTO, response.getBody());
    }

    @Test
    public void testAtualizarAvaliacao() {
        Avaliacao avaliacao = new Avaliacao();
        doNothing().when(avaliacaoService).atualizarAvaliacao(avaliacao);

        ResponseEntity<String> response = avaliacaoController.atualizarAvaliacao(1, avaliacao);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Avaliação atualizada com sucesso", response.getBody());
    }

    @Test
    public void testAtualizarAvaliacaoNotFound() {
        Avaliacao avaliacao = new Avaliacao();
        doThrow(new IllegalArgumentException("Not found")).when(avaliacaoService).atualizarAvaliacao(avaliacao);

        ResponseEntity<String> response = avaliacaoController.atualizarAvaliacao(1, avaliacao);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Erro ao atualizar avaliação: Not found", response.getBody());
    }

    @Test
    public void testDeletarAvaliacao() {
        doNothing().when(avaliacaoService).deletarAvaliacao(1);

        ResponseEntity<String> response = avaliacaoController.deletarAvaliacao(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Avaliação deletada com sucesso", response.getBody());
    }

    @Test
    public void testDeletarAvaliacaoNotFound() {
        doThrow(new IllegalArgumentException("Not found")).when(avaliacaoService).deletarAvaliacao(1);

        ResponseEntity<String> response = avaliacaoController.deletarAvaliacao(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Erro ao deletar avaliação: Not found", response.getBody());
    }
}
