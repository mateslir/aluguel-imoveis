package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.dao.ImovelRepository;
import br.com.alugueimoveis.Fourcamp.model.Imovel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ImovelRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ImovelRepository imovelRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvar() {
        Imovel imovel = new Imovel();
        // Configurar o objeto imovel com valores de teste
        imovel.setTipoImovel("Apartamento");
        imovel.setCepImovel("12345-678");
        imovel.setEnderecoImovel("Rua Teste, 123");
        imovel.setEstadoImovel("SP");
        imovel.setCidadeImovel("São Paulo");
        imovel.setBairroImovel("Centro");
        imovel.setAreaImovel(100.0);
        imovel.setTituloImovel("Imovel Teste");
        imovel.setDescricaoImovel("Descricao Teste");

        System.out.println("Testando método salvar...");

        int resultado = imovelRepository.salvar(imovel);

        System.out.println("Resultado do método salvar: " + resultado);

        assertEquals(1, resultado);
    }

    @Test
    public void testUpdateImovel() {
        // Criar objeto Imovel com dados de teste
        Imovel imovel = new Imovel();
        imovel.setId(1L);
        imovel.setTipoImovel("Casa");
        imovel.setCepImovel("12345-678");
        imovel.setEnderecoImovel("Rua A, 123");
        imovel.setEstadoImovel("SP");
        imovel.setCidadeImovel("São Paulo");
        imovel.setBairroImovel("Centro");
        imovel.setAreaImovel(100.0);
        imovel.setTituloImovel("Linda casa no centro");
        imovel.setDescricaoImovel("Casa espaçosa e bem localizada.");
        imovel.setPreco(500000.0);
        imovel.setClienteId(1L);

        // Chamar o método updateImovel do repositório
        imovelRepository.updateImovel(imovel);

        // Validação usando mocks para verificar se o método update foi chamado corretamente
        verify(jdbcTemplate).update(
                eq("UPDATE imovel SET tipoimovel = ?, cepimovel = ?, enderecoimovel = ?, estadoimovel = ?, cidadeimovel = ?, bairroimovel = ?, areaimovel = ?, tituloimovel = ?, descricaoimovel = ?, preco = ?, clienteid = ? WHERE id = ?"),
                eq(imovel.getTipoImovel()),
                eq(imovel.getCepImovel()),
                eq(imovel.getEnderecoImovel()),
                eq(imovel.getEstadoImovel()),
                eq(imovel.getCidadeImovel()),
                eq(imovel.getBairroImovel()),
                eq(imovel.getAreaImovel()),
                eq(imovel.getTituloImovel()),
                eq(imovel.getDescricaoImovel()),
                eq(imovel.getPreco()),
                eq(imovel.getClienteId()),
                eq(imovel.getId())
        );
    }


    @Test
    void testUpdateImovel_DataAccessException() {
        doThrow(DataAccessException.class).when(jdbcTemplate).update(any(String.class), any(Object[].class));

        assertThrows(DataAccessException.class, () -> imovelRepository.updateImovel(new Imovel()));
    }

    @Test
    void testDeleteImovel() {
        when(jdbcTemplate.update(anyString(), anyLong())).thenReturn(1);

        int result = imovelRepository.deleteImovel(1L);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyLong());
    }

    @Test
    void testFindByEstado() {
        List<Imovel> expected = Arrays.asList(new Imovel(), new Imovel());
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(ImovelRepository.ImovelRowMapper.class))).thenReturn(expected);

        List<Imovel> result = imovelRepository.findByEstado("SP");

        assertEquals(expected.size(), result.size());
        verify(jdbcTemplate, times(1)).query(anyString(), any(Object[].class), any(ImovelRepository.ImovelRowMapper.class));
    }

    @Test
    void testExistsById() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class))).thenReturn(1);

        boolean result = imovelRepository.existsById(1);

        assertTrue(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(Object[].class), eq(Integer.class));
    }
}
