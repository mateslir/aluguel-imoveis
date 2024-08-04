package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.dao.ImovelRepository;
import br.com.alugueimoveis.Fourcamp.exceptions.EstadoNaoEncontradoException;
import br.com.alugueimoveis.Fourcamp.model.Imovel;
import br.com.alugueimoveis.Fourcamp.usercase.ImovelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ImovelServiceTest {

    @Mock
    private ImovelRepository imovelRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ImovelService imovelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarImovelComSucesso() {
        Imovel imovel = new Imovel();
        imovel.setId(1L);
        imovel.setTipoImovel("Apartamento");
        imovel.setCepImovel("12345-678");
        imovel.setEnderecoImovel("Rua Exemplo, 123");
        imovel.setEstadoImovel("SP");
        imovel.setCidadeImovel("Cidade Exemplo");
        imovel.setBairroImovel("Bairro Exemplo");
        imovel.setAreaImovel(100.0);
        imovel.setTituloImovel("Título de Exemplo");
        imovel.setDescricaoImovel("Imóvel de exemplo para teste");
        imovel.setPreco(500000.0);
        imovel.setClienteId(1L);

        when(imovelRepository.salvar(imovel)).thenReturn(1);

        int id = imovelService.salvarImovel(imovel);

        assertEquals(1, id);
        verify(imovelRepository, times(1)).salvar(imovel);
    }

    @Test
    public void testSalvarImovelComErroDeValidacao() {
        Imovel imovel = new Imovel();
        imovel.setId(1L);
        imovel.setTipoImovel("Apartamento");
        imovel.setCepImovel("12345-678");
        imovel.setEnderecoImovel("");  // Endereço vazio para simular erro de validação
        imovel.setEstadoImovel("SP");
        imovel.setCidadeImovel("Cidade Exemplo");
        imovel.setBairroImovel("Bairro Exemplo");
        imovel.setAreaImovel(100.0);
        imovel.setTituloImovel("Título de Exemplo");
        imovel.setDescricaoImovel("Imóvel de exemplo para teste");
        imovel.setPreco(500000.0);
        imovel.setClienteId(1L);

        doThrow(new IllegalArgumentException("Erro de validação")).when(imovelRepository).salvar(imovel);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            imovelService.salvarImovel(imovel);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Endereço do imóvel não pode ser vazio", exception.getMessage());
    }

    @Test
    public void testSalvarImovelComErroNoBancoDeDados() {
        Imovel imovel = new Imovel();
        imovel.setId(1L);
        imovel.setTipoImovel("Apartamento");
        imovel.setCepImovel("12345-678");
        imovel.setEnderecoImovel("Rua Exemplo, 123");
        imovel.setEstadoImovel("SP");
        imovel.setCidadeImovel("Cidade Exemplo");
        imovel.setBairroImovel("Bairro Exemplo");
        imovel.setAreaImovel(100.0);
        imovel.setTituloImovel("Título de Exemplo");
        imovel.setDescricaoImovel("Imóvel de exemplo para teste");
        imovel.setPreco(500000.0);
        imovel.setClienteId(1L);

        doThrow(new DataAccessException("ID invalido.") {}).when(imovelRepository).salvar(imovel);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            imovelService.salvarImovel(imovel);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("ID invalido.", exception.getMessage());
    }

    @Test
    public void testUpdateImovelComSucesso() {
        Imovel imovel = new Imovel();
        imovel.setId(1L);
        imovel.setTipoImovel("Apartamento");
        imovel.setCepImovel("12345-678");
        imovel.setEnderecoImovel("Rua Exemplo, 123");
        imovel.setEstadoImovel("SP");
        imovel.setCidadeImovel("Cidade Exemplo");
        imovel.setBairroImovel("Bairro Exemplo");
        imovel.setAreaImovel(100.0);
        imovel.setTituloImovel("Título de Exemplo");
        imovel.setDescricaoImovel("Imóvel de exemplo para teste atualizado");
        imovel.setPreco(600000.0); // Atualizando o preço
        imovel.setClienteId(1L);

        when(imovelRepository.updateImovel(imovel)).thenReturn(1);

        int resultado = imovelService.updateImovel(imovel);

        assertEquals(1, resultado);
        verify(imovelRepository, times(1)).updateImovel(imovel);
    }

    @Test
    public void testDeleteImovelComSucesso() {
        Long id = 1L;

        when(imovelRepository.deleteImovel(id)).thenReturn(1);

        int resultado = imovelService.deleteImovel(id);

        assertEquals(1, resultado);
        verify(imovelRepository, times(1)).deleteImovel(id);
    }

    @Test
    public void testGetAllImoveis() {
        List<Imovel> imoveis = Arrays.asList(new Imovel(), new Imovel());

        when(imovelRepository.getAllImoveis()).thenReturn(imoveis);

        List<Imovel> resultado = imovelService.getAllImoveis();

        assertEquals(2, resultado.size());
        verify(imovelRepository, times(1)).getAllImoveis();
    }

    @Test
    public void testBuscarImoveisPorEstadoComSucesso() {
        List<Imovel> imoveis = Arrays.asList(new Imovel(), new Imovel());
        String estado = "São Paulo";

        when(imovelRepository.findByEstado(estado)).thenReturn(imoveis);

        List<Imovel> resultado = imovelService.buscarImoveisPorEstado(estado);

        assertEquals(2, resultado.size());
        verify(imovelRepository, times(1)).findByEstado(estado);
    }

    @Test
    public void testBuscarImoveisPorEstadoNaoEncontrado() {
        String estado = "SP";

        when(imovelRepository.findByEstado(estado)).thenReturn(Arrays.asList());

        Exception exception = assertThrows(EstadoNaoEncontradoException.class, () -> {
            imovelService.buscarImoveisPorEstado(estado);
        });

        assertEquals("Estado não encontrado: ", exception.getMessage());
        verify(imovelRepository, times(1)).findByEstado(estado);
    }
}
