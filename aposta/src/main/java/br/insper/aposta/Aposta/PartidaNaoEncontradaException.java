package br.insper.aposta.Aposta;
public class PartidaNaoEncontradaException extends RuntimeException{
    public PartidaNaoEncontradaException(Integer id) {
        super("Partida não encontrada com o id: " + id);
    }
}