package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.dao.ReservaRepository;
import br.com.alugueimoveis.Fourcamp.model.Reserva;
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

class ReservaRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ReservaRepository reservaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Reserva reserva = new Reserva();
        reserva.setIdCliente(1);
        reserva.setIdImovel(2);
        reserva.setDataInicio("2023-08-01");
        reserva.setDataFim("2023-08-10");
        reserva.setStatus("ativa");

        when(jdbcTemplate.update(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString())).thenReturn(1);

        int result = reservaRepository.save(reserva);
        assertEquals(1, result);
    }

    @Test
    void testExistsById() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class))).thenReturn(1);

        boolean result = reservaRepository.existsById(1);
        assertTrue(result);
    }

    @Test
    void testFindById() {
        Reserva expectedReserva = new Reserva();
        expectedReserva.setId(1);
        expectedReserva.setIdCliente(1);
        expectedReserva.setIdImovel(2);
        expectedReserva.setDataInicio("2023-08-01");
        expectedReserva.setDataFim("2023-08-10");
        expectedReserva.setStatus("ativa");

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(expectedReserva);

        Reserva actualReserva = reservaRepository.findById(1);
        assertEquals(expectedReserva, actualReserva);
    }

    @Test
    void testExistsByIdClienteAndIdImovel() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class))).thenReturn(1);

        boolean result = ReservaRepository.existsByIdClienteAndIdImovel(1, 2);
        assertTrue(result);
    }

    @Test
    void testUpdate() {
        Reserva reserva = new Reserva();
        reserva.setId(1);
        reserva.setIdCliente(1);
        reserva.setIdImovel(2);
        reserva.setDataInicio("2023-08-01");
        reserva.setDataFim("2023-08-10");
        reserva.setStatus("ativa");

        when(jdbcTemplate.update(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyInt())).thenReturn(1);

        reservaRepository.update(reserva);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void testDeleteById() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);

        int result = reservaRepository.deleteById(1);
        assertEquals(1, result);
    }
}
