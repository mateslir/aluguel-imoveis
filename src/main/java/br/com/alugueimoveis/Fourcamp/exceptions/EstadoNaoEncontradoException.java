package br.com.alugueimoveis.Fourcamp.exceptions;

public class EstadoNaoEncontradoException extends RuntimeException {
    public EstadoNaoEncontradoException(String estado) {
        super("Estado não encontrado: " + estado);
    }
}
