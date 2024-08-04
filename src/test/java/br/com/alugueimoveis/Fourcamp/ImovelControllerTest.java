package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.controller.ImovelController;
import br.com.alugueimoveis.Fourcamp.exceptions.EstadoNaoEncontradoException;
import br.com.alugueimoveis.Fourcamp.model.Imovel;
import br.com.alugueimoveis.Fourcamp.usercase.ImovelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImovelControllerTest {

    @InjectMocks
    private ImovelController imovelController;

    @Mock
    private ImovelService imovelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarImovel_Success() {
        Imovel imovel = new Imovel();
        when(imovelService.salvarImovel(imovel)).thenReturn(1);

        ResponseEntity<String> response = imovelController.cadastrarImovel(imovel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Imóvel cadastrado com sucesso", response.getBody());
    }

    @Test
    public void testCadastrarImovel_InternalServerError() {
        Imovel imovel = new Imovel();
        when(imovelService.salvarImovel(imovel)).thenReturn(0);

        ResponseEntity<String> response = imovelController.cadastrarImovel(imovel);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao cadastrar imóvel", response.getBody());
    }

    @Test
    public void testCadastrarImovel_ResponseStatusException() {
        Imovel imovel = new Imovel();
        when(imovelService.salvarImovel(imovel)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input"));

        ResponseEntity<String> response = imovelController.cadastrarImovel(imovel);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody());
    }

    @Test
    public void testCadastrarImovel_UnexpectedException() {
        Imovel imovel = new Imovel();
        when(imovelService.salvarImovel(imovel)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<String> response = imovelController.cadastrarImovel(imovel);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro inesperado: Unexpected error", response.getBody());
    }

    @Test
    public void testGetAllImoveis() {
        List<Imovel> imoveis = List.of(new Imovel());
        when(imovelService.getAllImoveis()).thenReturn(imoveis);

        ResponseEntity<List<Imovel>> response = imovelController.getAllImoveis();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imoveis, response.getBody());
    }

    @Test
    public void testBuscarImoveisPorEstado_Success() {
        String estado = "SP";
        List<Imovel> imoveis = List.of(new Imovel());
        when(imovelService.buscarImoveisPorEstado(estado)).thenReturn(imoveis);

        ResponseEntity<List<Imovel>> response = imovelController.buscarImoveisPorEstado(estado);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imoveis, response.getBody());
    }

    @Test
    public void testBuscarImoveisPorEstado_EstadoNaoEncontradoException() {
        String estado = "SP";
        when(imovelService.buscarImoveisPorEstado(estado)).thenThrow(new EstadoNaoEncontradoException("Estado não encontrado"));

        ResponseEntity<List<Imovel>> response = imovelController.buscarImoveisPorEstado(estado);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testBuscarImoveisPorEstado_UnexpectedException() {
        String estado = "SP";
        when(imovelService.buscarImoveisPorEstado(estado)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<List<Imovel>> response = imovelController.buscarImoveisPorEstado(estado);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testUpdateImovel_Success() {
        Imovel imovel = new Imovel();
        when(imovelService.updateImovel(imovel)).thenReturn(1);

        ResponseEntity<String> response = imovelController.updateImovel(1L, imovel);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Imóvel atualizado com sucesso", response.getBody());
    }

    @Test
    public void testUpdateImovel_InternalServerError() {
        Imovel imovel = new Imovel();
        when(imovelService.updateImovel(imovel)).thenReturn(0);

        ResponseEntity<String> response = imovelController.updateImovel(1L, imovel);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao atualizar imóvel", response.getBody());
    }

    @Test
    public void testDeleteImovel_Success() {
        when(imovelService.deleteImovel(1L)).thenReturn(1);

        ResponseEntity<String> response = imovelController.deleteImovel(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Imóvel deletado com sucesso", response.getBody());
    }

    @Test
    public void testDeleteImovel_InternalServerError() {
        when(imovelService.deleteImovel(1L)).thenReturn(0);

        ResponseEntity<String> response = imovelController.deleteImovel(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao deletar imóvel", response.getBody());
    }
}

