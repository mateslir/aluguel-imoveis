package br.com.alugueimoveis.Fourcamp.utils;
import br.com.alugueimoveis.Fourcamp.model.Imovel;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ImovelValidator {
    private static final Pattern CEP_PATTERN = Pattern.compile("^\\d{5}-\\d{3}$");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");
    public static void validar(Imovel imovel) {
        if (imovel.getTipoImovel() == null || imovel.getTipoImovel().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do imóvel não pode ser vazio");
        }
        if (imovel.getCepImovel() == null || imovel.getCepImovel().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP do imóvel não pode ser vazio");
        }
        if (!isValidCep(imovel.getCepImovel())) {
            throw new IllegalArgumentException("CEP do imóvel está em um formato inválido");
        }
        if (!isNumeric(imovel.getAreaImovel().toString())) {
            throw new IllegalArgumentException("Área do imóvel deve conter apenas números");
        }
        if (imovel.getEnderecoImovel() == null || imovel.getEnderecoImovel().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do imóvel não pode ser vazio");
        }
        if (imovel.getEstadoImovel() == null || imovel.getEstadoImovel().trim().isEmpty()) {
            throw new IllegalArgumentException("Estado do imóvel não pode ser vazio");
        }
        if (imovel.getCidadeImovel() == null || imovel.getCidadeImovel().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade do imóvel não pode ser vazia");
        }
        if (imovel.getBairroImovel() == null || imovel.getBairroImovel().trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro do imóvel não pode ser vazio");
        }
        if (imovel.getAreaImovel() == null || imovel.getAreaImovel() <= 0) {
            throw new IllegalArgumentException("Área do imóvel deve ser maior que zero");
        }
        if (imovel.getTituloImovel() == null || imovel.getTituloImovel().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do imóvel não pode ser vazio");
        }
        if (imovel.getDescricaoImovel() == null || imovel.getDescricaoImovel().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do imóvel não pode ser vazia");

        }
        if (imovel.getPreco() == null || imovel.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço do imóvel deve ser maior que zero");
        }
        if (!isNumeric(imovel.getPreco().toString())) {
            throw new IllegalArgumentException("Preço do imóvel deve conter apenas números");
        }

        if (imovel.getClienteId() == null || imovel.getClienteId() <= 0) {
            throw new IllegalArgumentException("O imóvel deve estar associado a um cliente válido");
        }
        if (!"Casa".equals(imovel.getTipoImovel()) && !"Apartamento".equals(imovel.getTipoImovel())) {
            throw new IllegalArgumentException("Tipo do Imóvel deve ser 'Casa' ou 'Apartamento'");
        }
        if (imovel.getEstadoImovel() != null) {
            if (imovel.getEstadoImovel().length() != 2) {
                throw new IllegalArgumentException("Estado deve ter exatamente 2 caracteres");
            } else {
                for (char c : imovel.getEstadoImovel().toCharArray()) {
                    if (!Character.isLetter(c)) {
                        throw new IllegalArgumentException("Estado não pode conter números");

                    }
                }
            }
        }
        // Validação (sem números)
        if (imovel.getCidadeImovel() != null) {
            for (char c : imovel.getCidadeImovel().toCharArray()) {
                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    throw new IllegalArgumentException("Cidade não pode conter números");

                }
            }
        }
        if (imovel.getBairroImovel() != null) {
            for (char c : imovel.getBairroImovel().toCharArray()) {
                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    throw new IllegalArgumentException("Bairro não pode conter números");

                }
            }
        }
        // Validação para clienteId
        if (imovel.getClienteId() == null || imovel.getClienteId() <= 0) {
            throw new IllegalArgumentException("O imóvel deve estar associado a um cliente cadastrado");
        }
    }
    private static boolean isValidCep(String cep) {
        Matcher matcher = CEP_PATTERN.matcher(cep);
        return matcher.matches();
    }
    private static boolean isNumeric(String str) {
        Matcher matcher = NUMERIC_PATTERN.matcher(str);
        return matcher.matches();
    }
}
