package br.com.alugueimoveis.Fourcamp.controller;

import br.com.alugueimoveis.Fourcamp.dtos.ReservaDTO;
import br.com.alugueimoveis.Fourcamp.model.Reserva;
import br.com.alugueimoveis.Fourcamp.usercase.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obterReservaPorId(@PathVariable int id) {
        try {
            ReservaDTO reservaDTO = reservaService.obterReservaPorId(id);
            return ResponseEntity.ok(reservaDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> adicionarReserva(@RequestBody Reserva reserva) {
        try {
            reservaService.adicionarReserva(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva criada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarReserva(@PathVariable int id, @RequestBody Reserva reserva) {
        reserva.setId(id);
        try {
            reservaService.atualizarReserva(reserva);
            return ResponseEntity.ok("Reserva atualizada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarReserva(@PathVariable int id) {
        try {
            reservaService.deletarReserva(id);
            return ResponseEntity.ok("Reserva deletada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}