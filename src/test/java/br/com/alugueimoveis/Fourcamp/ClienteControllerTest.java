package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.controller.ClienteController;
import br.com.alugueimoveis.Fourcamp.dtos.ClienteDTO;
import br.com.alugueimoveis.Fourcamp.model.Cliente;
import br.com.alugueimoveis.Fourcamp.usercase.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarCliente_Success() {
        Cliente cliente = new Cliente();
        when(clienteService.salvarCliente(cliente)).thenReturn(1);

        ResponseEntity<String> response = clienteController.cadastrarCliente(cliente);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente cadastrado com sucesso", response.getBody());
    }

    @Test
    public void testCadastrarCliente_InternalServerError() {
        Cliente cliente = new Cliente();
        when(clienteService.salvarCliente(cliente)).thenReturn(0);

        ResponseEntity<String> response = clienteController.cadastrarCliente(cliente);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao cadastrar cliente", response.getBody());
    }

    @Test
    public void testCadastrarCliente_ResponseStatusException() {
        Cliente cliente = new Cliente();
        when(clienteService.salvarCliente(cliente)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input"));

        ResponseEntity<String> response = clienteController.cadastrarCliente(cliente);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody());
    }

    @Test
    public void testCadastrarCliente_UnexpectedException() {
        Cliente cliente = new Cliente();
        when(clienteService.salvarCliente(cliente)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<String> response = clienteController.cadastrarCliente(cliente);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro inesperado: Unexpected error", response.getBody());
    }

    @Test
    public void testObterClientePorId_Success() {
        int id = 1;
        ClienteDTO clienteDTO = new ClienteDTO();
        when(clienteService.obterClientePorId(id)).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.obterClientePorId(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDTO, response.getBody());
    }

    @Test
    public void testObterClientePorId_NotFound() {
        int id = 1;
        when(clienteService.obterClientePorId(id)).thenReturn(null);

        ResponseEntity<ClienteDTO> response = clienteController.obterClientePorId(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testObterClientePorId_InternalServerError() {
        int id = 1;
        when(clienteService.obterClientePorId(id)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<ClienteDTO> response = clienteController.obterClientePorId(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
