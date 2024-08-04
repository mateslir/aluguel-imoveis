package br.com.alugueimoveis.Fourcamp.controller;
import br.com.alugueimoveis.Fourcamp.exceptions.EstadoNaoEncontradoException;
import br.com.alugueimoveis.Fourcamp.model.Imovel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import br.com.alugueimoveis.Fourcamp.usercase.ImovelService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/imoveis")
public class ImovelController {

    private final ImovelService imovelService;

    public ImovelController(ImovelService imovelService) {
        this.imovelService = imovelService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarImovel(@RequestBody Imovel imovel) {
        try {
            int result = imovelService.salvarImovel(imovel);
            if (result > 0) {
                return ResponseEntity.ok("Imóvel cadastrado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar imóvel");
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Imovel>> getAllImoveis() {
        List<Imovel> imoveis = imovelService.getAllImoveis();
        return new ResponseEntity<>(imoveis, HttpStatus.OK);
    }


    @GetMapping("/{estado}")
    public ResponseEntity<List<Imovel>> buscarImoveisPorEstado(@PathVariable String estado) {
        try {
            List<Imovel> imoveis = imovelService.buscarImoveisPorEstado(estado);
            return ResponseEntity.ok(imoveis);
        } catch (EstadoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateImovel(@PathVariable Long id, @Validated @RequestBody Imovel imovel) {
        imovel.setId(id);
        int result = imovelService.updateImovel(imovel);
        if (result > 0) {
            return ResponseEntity.ok("Imóvel atualizado com sucesso");
        } else {
            return ResponseEntity.status(500).body("Erro ao atualizar imóvel");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImovel(@PathVariable Long id) {
        int result = imovelService.deleteImovel(id);
        if (result > 0) {
            return ResponseEntity.ok("Imóvel deletado com sucesso");
        } else {
            return ResponseEntity.status(500).body("Erro ao deletar imóvel");
        }
    }
}