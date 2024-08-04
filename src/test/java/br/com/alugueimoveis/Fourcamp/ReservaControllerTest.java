package br.com.alugueimoveis.Fourcamp;

import br.com.alugueimoveis.Fourcamp.controller.ReservaController;
import br.com.alugueimoveis.Fourcamp.dtos.ReservaDTO;
import br.com.alugueimoveis.Fourcamp.model.Reserva;
import br.com.alugueimoveis.Fourcamp.usercase.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservaControllerTest {

    @InjectMocks
    private ReservaController reservaController;

    @Mock
    private ReservaService reservaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObterReservaPorId_Success() {
        int id = 1;
        ReservaDTO reservaDTO = new ReservaDTO();
        when(reservaService.obterReservaPorId(id)).thenReturn(reservaDTO);

        ResponseEntity<ReservaDTO> response = reservaController.obterReservaPorId(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservaDTO, response.getBody());
    }

    @Test
    public void testObterReservaPorId_NotFound() {
        int id = 1;
        when(reservaService.obterReservaPorId(id)).thenThrow(new IllegalArgumentException());

        ResponseEntity<ReservaDTO> response = reservaController.obterReservaPorId(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testAdicionarReserva_Success() {
        Reserva reserva = new Reserva();
        doNothing().when(reservaService).adicionarReserva(reserva);

        ResponseEntity<String> response = reservaController.adicionarReserva(reserva);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Reserva criada com sucesso.", response.getBody());
    }

    @Test
    public void testAdicionarReserva_BadRequest() {
        Reserva reserva = new Reserva();
        doThrow(new IllegalArgumentException("Invalid data")).when(reservaService).adicionarReserva(reserva);

        ResponseEntity<String> response = reservaController.adicionarReserva(reserva);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid data", response.getBody());
    }

    @Test
    public void testAtualizarReserva_Success() {
        Reserva reserva = new Reserva();
        doNothing().when(reservaService).atualizarReserva(reserva);

        ResponseEntity<String> response = reservaController.atualizarReserva(1, reserva);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Reserva atualizada com sucesso.", response.getBody());
    }

    @Test
    public void testAtualizarReserva_NotFound() {
        Reserva reserva = new Reserva();
        doThrow(new IllegalArgumentException("Reserva not found")).when(reservaService).atualizarReserva(reserva);

        ResponseEntity<String> response = reservaController.atualizarReserva(1, reserva);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Reserva not found", response.getBody());
    }

    @Test
    public void testDeletarReserva_Success() {
        int id = 1;
        when(reservaService.deletarReserva(id)).thenReturn(1); // Supondo que retorne 1 em caso de sucesso

        ResponseEntity<String> response = reservaController.deletarReserva(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Reserva deletada com sucesso.", response.getBody());
    }

    @Test
    public void testDeletarReserva_NotFound() {
        int id = 1;
        doThrow(new IllegalArgumentException("Reserva not found")).when(reservaService).deletarReserva(id);

        ResponseEntity<String> response = reservaController.deletarReserva(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Reserva not found", response.getBody());
    }
}
