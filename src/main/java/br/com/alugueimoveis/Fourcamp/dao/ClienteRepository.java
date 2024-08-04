package br.com.alugueimoveis.Fourcamp.dao;

import br.com.alugueimoveis.Fourcamp.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;



@Repository
public class ClienteRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, email, senha) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), cliente.getSenha());
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    public Cliente findById(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Cliente cliente = new Cliente();
            cliente.setId((long) rs.getInt("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setEmail(rs.getString("email"));
            cliente.setSenha(rs.getString("senha"));
            return cliente;
        });
    }
}
