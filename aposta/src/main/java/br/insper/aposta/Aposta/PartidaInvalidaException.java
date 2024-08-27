package br.insper.aposta.Aposta;

public class PartidaInvalidaException extends RuntimeException {
    public PartidaInvalidaException() {
        super("Aposta inválida, partida já ocorreu.");
    }
}
