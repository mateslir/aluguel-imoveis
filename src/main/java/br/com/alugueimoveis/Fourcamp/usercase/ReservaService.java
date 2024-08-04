package br.com.alugueimoveis.Fourcamp.usercase;

import br.com.alugueimoveis.Fourcamp.dao.ReservaRepository;
import br.com.alugueimoveis.Fourcamp.dtos.ReservaDTO;
import br.com.alugueimoveis.Fourcamp.model.Reserva;
import br.com.alugueimoveis.Fourcamp.utils.ReservaValidator;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaValidator reservaValidator;

    public ReservaService(ReservaRepository reservaRepository, ReservaValidator reservaValidator) {
        this.reservaRepository = reservaRepository;
        this.reservaValidator = reservaValidator;
    }



    public ReservaDTO obterReservaPorId(int id) {
        if (!reservaRepository.existsById(id)) {
            throw new IllegalArgumentException("ID da reserva não existe.");
        }

        Reserva reserva = reservaRepository.findById(id);
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }

        return converterParaDTO(reserva);
    }

    private ReservaDTO converterParaDTO(Reserva reserva) {
        return new ReservaDTO(
                reserva.getIdImovel(),
                reserva.getIdCliente(),
                reserva.getDataInicio(),
                reserva.getDataFim(),
                reserva.getStatus()

        );
    }
    public void adicionarReserva(Reserva reserva) {
        reservaValidator.validarReserva(reserva, false);
        reservaRepository.save(reserva);
    }

    public void atualizarReserva(Reserva reserva) {
        if (!reservaRepository.existsById(reserva.getId())) {
            throw new IllegalArgumentException("ID da reserva não existe.");
        }
        reservaValidator.validarReserva(reserva, true);
        reservaRepository.update(reserva);
    }


    public int deletarReserva(int id) {
        if (!reservaRepository.existsById(id)) {
            throw new IllegalArgumentException("ID da reserva não existe.");
        }
        return reservaRepository.deleteById(id);
    }
}
