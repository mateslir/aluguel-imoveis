package br.com.alugueimoveis.Fourcamp.usercase;
import br.com.alugueimoveis.Fourcamp.dao.ClienteRepository;
import br.com.alugueimoveis.Fourcamp.dtos.ClienteDTO;
import br.com.alugueimoveis.Fourcamp.model.Cliente;
import br.com.alugueimoveis.Fourcamp.utils.ClienteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public int salvarCliente(Cliente cliente) {
        try {
            ClienteValidator.validar(cliente);

            if (clienteRepository.emailExists(cliente.getEmail())) {
                throw new IllegalArgumentException("Seu email já foi cadastrado");
            }

            return clienteRepository.salvar(cliente);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao acessar o banco de dados.");
        }
    }
    public ClienteDTO obterClientePorId(int id) {
        Cliente cliente = clienteRepository.findById(id);
        return cliente != null ? convertToDTO(cliente) : null;
    }
    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setEmail(cliente.getEmail());
        return clienteDTO;
    }

}