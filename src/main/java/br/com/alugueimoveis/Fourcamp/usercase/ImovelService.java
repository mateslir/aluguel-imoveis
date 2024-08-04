package br.com.alugueimoveis.Fourcamp.usercase;
import br.com.alugueimoveis.Fourcamp.dao.ImovelRepository;
import br.com.alugueimoveis.Fourcamp.exceptions.EstadoNaoEncontradoException;
import br.com.alugueimoveis.Fourcamp.model.Imovel;
import br.com.alugueimoveis.Fourcamp.utils.ImovelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ImovelService {
    private final ImovelRepository imovelRepository;

    public ImovelService(ImovelRepository imovelRepository, JdbcTemplate jdbcTemplate) {
        this.imovelRepository = imovelRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int salvarImovel(Imovel imovel) {
        try {
            ImovelValidator.validar(imovel);
            return imovelRepository.salvar(imovel);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ID invalido.");
        }
    }
    public int updateImovel(Imovel imovel) {
        return imovelRepository.updateImovel(imovel);
    }

    public int deleteImovel(Long id) {
        return imovelRepository.deleteImovel(id);
    }
    public List<Imovel> getAllImoveis() {
        return imovelRepository.getAllImoveis();
    }

    public List<Imovel> buscarImoveisPorEstado(String estado) {
        List<Imovel> imoveis = imovelRepository.findByEstado(estado);
        if (imoveis.isEmpty()) {
            throw new EstadoNaoEncontradoException(estado);
        }
        return imoveis;
    }
    }
