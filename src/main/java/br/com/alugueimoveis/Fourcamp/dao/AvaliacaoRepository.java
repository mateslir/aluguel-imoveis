package br.com.alugueimoveis.Fourcamp.dao;


import br.com.alugueimoveis.Fourcamp.model.Avaliacao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AvaliacaoRepository{

    private static JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public AvaliacaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Avaliacao> findAll() {
        String sql = "SELECT * FROM avaliacao";
        return jdbcTemplate.query(sql, (rs) -> {
            List<Avaliacao> avaliacoes = new ArrayList<>();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setIdImovel(rs.getInt("idImovel"));
                avaliacao.setIdCliente(rs.getInt("idCliente"));
                avaliacao.setNota(rs.getInt("nota"));
                avaliacao.setComentarios(rs.getString("comentarios"));
                avaliacoes.add(avaliacao);
            }
            return avaliacoes;
        });
    }
    public List<Avaliacao> findByIdImovel(int idImovel) {
        String sql = "SELECT * FROM avaliacao WHERE idImovel = ?";
        return jdbcTemplate.query(sql, new Object[]{idImovel}, (rs, rowNum) -> {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setId(rs.getInt("id"));
            avaliacao.setIdImovel(rs.getInt("idImovel"));
            avaliacao.setIdCliente(rs.getInt("idCliente"));
            avaliacao.setNota(rs.getInt("nota"));
            avaliacao.setComentarios(rs.getString("comentarios"));
            return avaliacao;
        });
    }
            public Avaliacao findById(int id) {
                String sql = "SELECT * FROM avaliacao WHERE id = ?";
                try {
                    return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                        Avaliacao avaliacao = new Avaliacao();
                        avaliacao.setId(rs.getInt("id"));
                        avaliacao.setIdImovel(rs.getInt("idImovel"));
                        avaliacao.setIdCliente(rs.getInt("idCliente"));
                        avaliacao.setNota(rs.getInt("nota"));
                        avaliacao.setComentarios(rs.getString("comentarios"));
                        return avaliacao;
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;  // Retorna null se a avaliação não for encontrada
        }
    }
    public static boolean existsByIdClienteAndIdImovel(long idImovel, long idCliente) {
        String sql = "SELECT COUNT(*) FROM avaliacao WHERE idimovel = ? AND idcliente = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{idImovel, idCliente}, Integer.class);
        return count != null && count > 0;
    }
    public void save(Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacao (idimovel, idcliente, nota, comentarios) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, avaliacao.getIdImovel(), avaliacao.getIdCliente(), avaliacao.getNota(), avaliacao.getComentarios());
    }

    public void updateAvaliacao(Avaliacao avaliacao) {
        String sql = "UPDATE avaliacao SET idimovel = ?, idcliente = ?, nota = ?, comentarios = ? WHERE id = ?";
        jdbcTemplate.update(sql, avaliacao.getIdImovel(), avaliacao.getIdCliente(), avaliacao.getNota(), avaliacao.getComentarios(), avaliacao.getId());

    }

    public void deleteAvaliacao(int id) {
        String sql = "DELETE FROM avaliacao WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}