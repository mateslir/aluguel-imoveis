package br.com.alugueimoveis.Fourcamp.utils;

import br.com.alugueimoveis.Fourcamp.dao.ClienteRepository;
import br.com.alugueimoveis.Fourcamp.dao.ImovelRepository;
import br.com.alugueimoveis.Fourcamp.model.Reserva;
import org.springframework.stereotype.Component;
import br.com.alugueimoveis.Fourcamp.dao.ReservaRepository;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ReservaValidator {

    private final ClienteRepository clienteRepository;
    private final ImovelRepository imovelRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final Date dataMinima;
    private final Date dataMaxima;

    public ReservaValidator(ClienteRepository clienteRepository, ImovelRepository imovelRepository) {
        this.clienteRepository = clienteRepository;
        this.imovelRepository = imovelRepository;
        this.sdf.setLenient(false);

        try {
            this.dataMinima = sdf.parse("2000-01-01");
            this.dataMaxima = sdf.parse("2100-12-31");
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao definir datas limites", e);
        }
    }

    public void validarReserva(Reserva reserva, boolean isAtualizacao) throws IllegalArgumentException {
        StringBuilder erros = new StringBuilder();

        if (!isAtualizacao) {
            // Verificar se o idCliente é válido
            if (reserva.getIdCliente() <= 0 || !clienteRepository.existsById(reserva.getIdCliente())) {
                erros.append("Id do cliente é obrigatório, e deve existir. ");
            }

            // Verificar se o idImovel é válido
            if (reserva.getIdImovel() <= 0 || !imovelRepository.existsById(reserva.getIdImovel())) {
                erros.append("Id do imóvel é obrigatório, e deve existir. ");
            }
        }
        if (ReservaRepository.existsByIdClienteAndIdImovel(reserva.getIdCliente(), reserva.getIdImovel())) {
            erros.append("Cliente já reservou o mesmo imóvel anteriormente. ");
        }


        // Verificar se a data de início está no formato correto e é válida
        if (!isDataValida(reserva.getDataInicio())) {
            erros.append("Data de início deve estar no formato yyyy-MM-dd, ser válida e estar entre 2000-01-01 e 2100-12-31. ");
        }

        // Verificar se a data de fim está no formato correto e é válida
        if (!isDataValida(reserva.getDataFim())) {
            erros.append("Data de fim deve estar no formato yyyy-MM-dd, ser válida e estar entre 2000-01-01 e 2100-12-31. ");
        }

        // Verificar se a data de início não é maior que a data de fim
        if (isDataValida(reserva.getDataInicio()) && isDataValida(reserva.getDataFim())) {
            try {
                Date dataInicio = sdf.parse(reserva.getDataInicio());
                Date dataFim = sdf.parse(reserva.getDataFim());
                if (dataInicio.after(dataFim)) {
                    erros.append("A data de início não pode ser maior que a data de fim. ");
                }
            } catch (ParseException e) {
                erros.append("Erro ao analisar as datas. ");
            }
        }

        // Verificar se o status é válido
        if (!"CONFIRMADA".equalsIgnoreCase(reserva.getStatus()) && !"RECUSADA".equalsIgnoreCase(reserva.getStatus())) {
            erros.append("Status deve ser CONFIRMADA ou RECUSADA. ");
        }

        if (erros.length() > 0) {
            throw new IllegalArgumentException(erros.toString().trim());
        }
    }
    private boolean isDataValida(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }
        try {
            Date dataFormatada = sdf.parse(data);
            return dataFormatada.compareTo(dataMinima) >= 0 && dataFormatada.compareTo(dataMaxima) <= 0 && sdf.format(dataFormatada).equals(data);
        } catch (ParseException e) {
            return false;
        }
    }
}
