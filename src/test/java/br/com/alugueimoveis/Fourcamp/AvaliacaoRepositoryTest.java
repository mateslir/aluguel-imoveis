package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.dao.AvaliacaoRepository;
import br.com.alugueimoveis.Fourcamp.model.Avaliacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AvaliacaoRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AvaliacaoRepository avaliacaoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Avaliacao avaliacao1 = new Avaliacao();
        Avaliacao avaliacao2 = new Avaliacao();
        List<Avaliacao> expectedList = Arrays.asList(avaliacao1, avaliacao2);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedList);

        List<Avaliacao> actualList = avaliacaoRepository.findAll();
        assertEquals(expectedList, actualList);
    }

    @Test
    void testFindByIdImovel() {
        Avaliacao avaliacao = new Avaliacao();
        List<Avaliacao> expectedList = Arrays.asList(avaliacao);

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(expectedList);

        List<Avaliacao> actualList = avaliacaoRepository.findByIdImovel(1);
        assertEquals(expectedList, actualList);
    }

    @Test
    void testFindById() {
        Avaliacao expectedAvaliacao = new Avaliacao();
        expectedAvaliacao.setId(1);

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(expectedAvaliacao);

        Avaliacao actualAvaliacao = avaliacaoRepository.findById(1);
        assertEquals(expectedAvaliacao, actualAvaliacao);
    }

    @Test
    void testFindById_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenThrow(new EmptyResultDataAccessException(1));

        Avaliacao actualAvaliacao = avaliacaoRepository.findById(1);
        assertNull(actualAvaliacao);
    }

    @Test
    void testExistsByIdClienteAndIdImovel() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class))).thenReturn(1);

        boolean result = AvaliacaoRepository.existsByIdClienteAndIdImovel(1L, 1L);
        assertTrue(result);
    }

    @Test
    void testSave() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdImovel(1);
        avaliacao.setIdCliente(1);
        avaliacao.setNota(5);
        avaliacao.setComentarios("Good");

        jdbcTemplate.update(anyString(), anyInt(), anyInt(), anyInt(), anyString());

        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyInt(), anyInt(), anyString());
    }

    @Test
    void testUpdateAvaliacao() {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(1);
        avaliacao.setIdImovel(1);
        avaliacao.setIdCliente(1);
        avaliacao.setNota(5);
        avaliacao.setComentarios("Good");

        jdbcTemplate.update(anyString(), anyInt(), anyInt(), anyInt(), anyString(), anyInt());

        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyInt(), anyInt(), anyString(), anyInt());
    }

    @Test
    void testDeleteAvaliacao() {
        jdbcTemplate.update(anyString(), anyInt());

        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }
}
