package br.com.alugueimoveis.Fourcamp.dao;

import br.com.alugueimoveis.Fourcamp.model.Reserva;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class ReservaRepository {

    private static JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public ReservaRepository(JdbcTemplate jdbcTemplate) {
        ReservaRepository.jdbcTemplate = jdbcTemplate;
    }



    public int save(Reserva reserva) {
        String sql = "INSERT INTO reserva (idCliente, idImovel, dataInicio, dataFim, status) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, reserva.getIdCliente(), reserva.getIdImovel(), reserva.getDataInicio(), reserva.getDataFim(), reserva.getStatus());
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM reserva WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    public Reserva findById(int id) {
        String sql = "SELECT * FROM reserva WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ReservaRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean existsByIdClienteAndIdImovel(long idCliente, long idImovel) {
        String sql = "SELECT COUNT(*) FROM reserva WHERE idcliente = ? AND idimovel = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{idCliente, idImovel}, Integer.class);
        return count != null && count > 0;
    }
    public void update(Reserva reserva) {
        String sql = "UPDATE reserva SET idCliente = ?, idImovel = ?, dataInicio = ?, dataFim = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql, reserva.getIdCliente(), reserva.getIdImovel(), reserva.getDataInicio(), reserva.getDataFim(), reserva.getStatus(), reserva.getId());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM reserva WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static class ReservaRowMapper implements RowMapper<Reserva> {
        @Override
        public Reserva mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reserva reserva = new Reserva();
            reserva.setId(rs.getInt("id"));
            reserva.setIdCliente(rs.getInt("idCliente"));
            reserva.setIdImovel(rs.getInt("idImovel"));
            reserva.setDataInicio(rs.getString("dataInicio"));
            reserva.setDataFim(rs.getString("dataFim"));
            reserva.setStatus(rs.getString("status"));
            return reserva;
        }
    }
}



