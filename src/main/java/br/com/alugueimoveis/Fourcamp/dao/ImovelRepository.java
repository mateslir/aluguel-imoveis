package br.com.alugueimoveis.Fourcamp.dao;
import br.com.alugueimoveis.Fourcamp.model.Imovel;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ImovelRepository {
    private final JdbcTemplate jdbcTemplate;

    public ImovelRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int salvar(Imovel imovel) {
        String sql = "INSERT INTO imovel (tipoimovel, cepimovel, enderecoimovel, estadoimovel, cidadeimovel, bairroimovel, areaimovel, tituloimovel, descricaoimovel, preco, clienteid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql,
                    imovel.getTipoImovel(),
                    imovel.getCepImovel(),
                    imovel.getEnderecoImovel(),
                    imovel.getEstadoImovel(),
                    imovel.getCidadeImovel(),
                    imovel.getBairroImovel(),
                    imovel.getAreaImovel(),
                    imovel.getTituloImovel(),
                    imovel.getDescricaoImovel(),
                    imovel.getPreco(),
                    imovel.getClienteId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ID tem que estar cadastrado");
        }
    }


    public List<Imovel> getAllImoveis() {
        String sql = "SELECT * FROM imovel";
        return jdbcTemplate.query(sql, new ImovelRowMapper());
    }

    private static final String SQL_FIND_BY_ESTADO = "SELECT * FROM imovel WHERE estadoImovel = ?";


    public List<Imovel> findByEstado(String estadoImovel) {
        return jdbcTemplate.query(SQL_FIND_BY_ESTADO, new Object[]{estadoImovel}, new ImovelRowMapper());
    }


    public static class ImovelRowMapper implements RowMapper<Imovel> {
        @Override
        public Imovel mapRow(ResultSet rs, int rowNum) throws SQLException {
            Imovel imovel = new Imovel();
            imovel.setId(rs.getLong("id"));
            imovel.setTipoImovel(rs.getString("tipoimovel"));
            imovel.setCepImovel(rs.getString("cepimovel"));
            imovel.setEnderecoImovel(rs.getString("enderecoimovel"));
            imovel.setEstadoImovel(rs.getString("estadoimovel"));
            imovel.setCidadeImovel(rs.getString("cidadeimovel"));
            imovel.setBairroImovel(rs.getString("bairroimovel"));
            imovel.setAreaImovel(rs.getDouble("areaimovel"));
            imovel.setTituloImovel(rs.getString("tituloimovel"));
            imovel.setDescricaoImovel(rs.getString("descricaoimovel"));
            imovel.setPreco(rs.getDouble("preco"));
            imovel.setClienteId(rs.getLong("clienteid"));
            return imovel;
        }
    }
    public int updateImovel(Imovel imovel) {
        String sql = "UPDATE imovel SET tipoimovel = ?, cepimovel = ?, enderecoimovel = ?, estadoimovel = ?, cidadeimovel = ?, bairroimovel = ?, areaimovel = ?, tituloimovel = ?, descricaoimovel = ?, preco = ?, clienteid = ? WHERE id = ?";
        jdbcTemplate.update(sql, imovel.getTipoImovel(), imovel.getCepImovel(), imovel.getEnderecoImovel(),
                imovel.getEstadoImovel(), imovel.getCidadeImovel(), imovel.getBairroImovel(),
                imovel.getAreaImovel(), imovel.getTituloImovel(), imovel.getDescricaoImovel(),
                imovel.getPreco(), imovel.getClienteId(), imovel.getId());
        return 0;
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM imovel WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }
    
    public int deleteImovel(Long id) {
        String sql = "DELETE FROM imovel WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }
}