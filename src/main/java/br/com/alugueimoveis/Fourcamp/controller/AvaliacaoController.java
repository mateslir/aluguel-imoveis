package br.com.alugueimoveis.Fourcamp.controller;

import br.com.alugueimoveis.Fourcamp.dtos.AvaliacaoDTO;
import br.com.alugueimoveis.Fourcamp.model.Avaliacao;
import br.com.alugueimoveis.Fourcamp.usercase.AvaliacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<String> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
        try {
            avaliacaoService.criarAvaliacao(avaliacao);
            return ResponseEntity.ok("Avaliação criada com sucesso");
        } catch (IllegalArgumentException e) {
            // Captura a exceção e retorna a mensagem com status apropriado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar avaliação: " + e.getMessage());
        } catch (Exception e) {
            // Captura outros erros inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Avaliacao>> obterTodasAvaliacoes() {
        try {
            List<Avaliacao> avaliacoes = avaliacaoService.obterTodasAvaliacoes();
            if (avaliacoes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(avaliacoes);
        } catch (Exception e) {
            // Retorna 500 Internal Server Error se houver exceção
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/imovel/{idImovel}")
    public ResponseEntity<List<AvaliacaoDTO>> obterAvaliacoesPorIdImovel(@PathVariable int idImovel) {
        try {
            List<AvaliacaoDTO> avaliacoesDTO = avaliacaoService.obterAvaliacoesPorIdImovel(idImovel);
            return ResponseEntity.ok(avaliacoesDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAvaliacao(@PathVariable int id, @RequestBody Avaliacao avaliacao) {
        try {
            avaliacao.setId(id);
            avaliacaoService.atualizarAvaliacao(avaliacao);
            return ResponseEntity.ok("Avaliação atualizada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar avaliação: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable int id) {
        try {
            avaliacaoService.deletarAvaliacao(id);
            return ResponseEntity.ok("Avaliação deletada com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar avaliação: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    }
