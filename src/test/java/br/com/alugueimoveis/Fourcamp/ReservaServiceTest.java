package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.dao.ReservaRepository;
import br.com.alugueimoveis.Fourcamp.dtos.ReservaDTO;
import br.com.alugueimoveis.Fourcamp.model.Reserva;
import br.com.alugueimoveis.Fourcamp.usercase.ReservaService;
import br.com.alugueimoveis.Fourcamp.utils.ReservaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaValidator reservaValidator;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObterReservaPorIdComSucesso() {
        Reserva reserva = new Reserva(1, 1, 1, "2023-08-01", "2023-08-04", "CONFIRMADO");

        when(reservaRepository.existsById(1)).thenReturn(true);
        when(reservaRepository.findById(1)).thenReturn(reserva);

        ReservaDTO reservaDTO = reservaService.obterReservaPorId(1);

        assertNotNull(reservaDTO);
        assertEquals(1, reservaDTO.getIdImovel());
        assertEquals(1, reservaDTO.getIdCliente());
        assertEquals("2023-08-01", reservaDTO.getDataInicio());
        assertEquals("2023-08-04", reservaDTO.getDataFim());
        verify(reservaRepository, times(1)).findById(1);
    }

    @Test
    public void testObterReservaPorIdNaoExistente() {
        when(reservaRepository.existsById(1)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservaService.obterReservaPorId(1);
        });

        assertEquals("ID da reserva não existe.", exception.getMessage());
        verify(reservaRepository, never()).findById(1);
    }

    @Test
    public void testAdicionarReservaComSucesso() {
        Reserva reserva = new Reserva(1, 1, 1, "2023-08-01", "2023-08-04", "PENDENTE");

        doNothing().when(reservaValidator).validarReserva(reserva, false);

        reservaService.adicionarReserva(reserva);

        verify(reservaValidator, times(1)).validarReserva(reserva, false);
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    public void testAtualizarReservaComSucesso() {
        Reserva reserva = new Reserva(1, 1, 1, "2023-08-01", "2023-08-04", "PENDENTE");

        when(reservaRepository.existsById(reserva.getId())).thenReturn(true);
        doNothing().when(reservaValidator).validarReserva(reserva, true);

        reservaService.atualizarReserva(reserva);

        verify(reservaRepository, times(1)).existsById(reserva.getId());
        verify(reservaValidator, times(1)).validarReserva(reserva, true);
        verify(reservaRepository, times(1)).update(reserva);
    }

    @Test
    public void testAtualizarReservaNaoExistente() {
        Reserva reserva = new Reserva(1, 1, 1, "2023-08-01", "2023-08-04", "PENDENTE");

        when(reservaRepository.existsById(reserva.getId())).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservaService.atualizarReserva(reserva);
        });

        assertEquals("ID da reserva não existe.", exception.getMessage());
        verify(reservaRepository, times(1)).existsById(reserva.getId());
        verify(reservaValidator, never()).validarReserva(reserva, true);
        verify(reservaRepository, never()).update(reserva);
    }

    @Test
    public void testDeletarReservaComSucesso() {
        int id = 1;

        when(reservaRepository.existsById(id)).thenReturn(true);
        when(reservaRepository.deleteById(id)).thenReturn(1);

        int resultado = reservaService.deletarReserva(id);

        assertEquals(1, resultado);
        verify(reservaRepository, times(1)).existsById(id);
        verify(reservaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletarReservaNaoExistente() {
        int id = 1;

        when(reservaRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservaService.deletarReserva(id);
        });

        assertEquals("ID da reserva não existe.", exception.getMessage());
        verify(reservaRepository, times(1)).existsById(id);
        verify(reservaRepository, never()).deleteById(id);
    }
}
