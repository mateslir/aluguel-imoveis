package br.com.alugueimoveis.Fourcamp.utils;

import br.com.alugueimoveis.Fourcamp.model.Cliente;

public class ClienteValidator {

    // Método para validar o cliente
    public static void validar(Cliente cliente) throws IllegalArgumentException {
        // Validação do nome
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (cliente.getNome() != null && !cliente.getNome().matches("^[A-Za-z ]+$")) {
            throw new IllegalArgumentException("Nome não pode conter números");
        }

        // Validação do email
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
        if (!cliente.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        // Validação da senha
        if (cliente.getSenha() != null) {
            if (cliente.getSenha().length() < 8) {
                throw new IllegalArgumentException("Senha deve ter pelo menos 8 caracteres");
            }
            if (!cliente.getSenha().matches(".*[A-Z].*")) {
                throw new IllegalArgumentException("Senha deve conter pelo menos uma letra maiúscula");
            }
            if (!cliente.getSenha().matches(".*[a-z].*")) {
                throw new IllegalArgumentException("Senha deve conter pelo menos uma letra minúscula");
            }
            if (!cliente.getSenha().matches(".*\\d.*")) {
                throw new IllegalArgumentException("Senha deve conter pelo menos um número");
            }
            if (!cliente.getSenha().matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                throw new IllegalArgumentException("Senha deve conter pelo menos um caractere especial");
            }
        }
    }
}

