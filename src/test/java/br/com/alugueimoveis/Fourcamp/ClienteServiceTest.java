package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.dao.ClienteRepository;
import br.com.alugueimoveis.Fourcamp.dtos.ClienteDTO;
import br.com.alugueimoveis.Fourcamp.model.Cliente;
import br.com.alugueimoveis.Fourcamp.usercase.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testSalvarClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNome("Thomas"); // Nome válido
        cliente.setEmail("joao@example.com");
        cliente.setSenha("C@sa150599");
        // Simulação de comportamento esperado
        when(clienteRepository.emailExists(cliente.getEmail())).thenReturn(false);
        when(clienteRepository.salvar(cliente)).thenReturn(1);

        int id = clienteService.salvarCliente(cliente);

        assertEquals(1, id);
        verify(clienteRepository, times(1)).salvar(cliente);
    }

    @Test
    public void testSalvarClienteComEmailExistente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Thomas");
        cliente.setEmail("joao@example.com");
        cliente.setSenha("C@sa150599");
        // Simular que a validação falha devido ao nome contendo números
        doThrow(new IllegalArgumentException("Nome não pode conter números")).when(clienteRepository).emailExists(cliente.getEmail());

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            clienteService.salvarCliente(cliente);
        });

        assertEquals(HttpStatus.BAD_REQUEST, ((ResponseStatusException) exception).getStatusCode());
        assertEquals("400 BAD_REQUEST \"Nome não pode conter números\"", exception.getMessage());
    }

    @Test
    public void testSalvarClienteComErroNoBancoDeDados() {
        Cliente cliente = new Cliente();
        cliente.setNome("João"); // Nome válido
        cliente.setEmail("joao@example.com");
        cliente.setSenha("C@sa150599");
        // Simulação de erro no banco de dados
        doThrow(new DataAccessException("Erro no banco de dados") {}).when(clienteRepository).salvar(cliente);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteService.salvarCliente(cliente);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Erro ao acessar o banco de dados.", exception.getMessage());
    }


    @Test
    public void testObterClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEmail("joao@example.com");

        when(clienteRepository.findById(1)).thenReturn(cliente);

        ClienteDTO clienteDTO = clienteService.obterClientePorId(1);

        assertNotNull(clienteDTO);
        assertEquals(cliente.getNome(), clienteDTO.getNome());
        assertEquals(cliente.getEmail(), clienteDTO.getEmail());
    }

    @Test
    public void testObterClientePorIdNaoEncontrado() {
        when(clienteRepository.findById(1)).thenReturn(null);

        ClienteDTO clienteDTO = clienteService.obterClientePorId(1);

        assertNull(clienteDTO);
    }
}
