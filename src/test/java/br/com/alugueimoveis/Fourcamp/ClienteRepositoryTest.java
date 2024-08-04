package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.dao.ClienteRepository;
import br.com.alugueimoveis.Fourcamp.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ClienteRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvar() {
        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setEmail("johndoe@example.com");
        cliente.setSenha("password");

        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString())).thenReturn(1);

        int result = clienteRepository.salvar(cliente);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), eq(cliente.getNome()), eq(cliente.getEmail()), eq(cliente.getSenha()));
    }

    @Test
    void testEmailExists() {
        String email = "johndoe@example.com";

        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyString())).thenReturn(1);

        boolean result = clienteRepository.emailExists(email);

        assertTrue(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class), eq(email));
    }

    @Test
    void testExistsById() {
        int id = 1;

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class))).thenReturn(1);

        boolean result = clienteRepository.existsById(id);

        assertTrue(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(Object[].class), eq(Integer.class));
    }

    @Test
    void testFindById() {
        int id = 1;
        Cliente expectedCliente = new Cliente();
        expectedCliente.setId((long) id);
        expectedCliente.setNome("John Doe");
        expectedCliente.setEmail("johndoe@example.com");
        expectedCliente.setSenha("password");

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(expectedCliente);

        Cliente actualCliente = clienteRepository.findById(id);

        assertEquals(expectedCliente, actualCliente);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(Object[].class), any(RowMapper.class));
    }
}