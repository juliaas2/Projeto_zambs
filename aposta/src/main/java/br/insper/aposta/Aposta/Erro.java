package br.insper.aposta.Aposta;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
public class Erro {
    private String mensagem;
    private LocalDateTime data;
    private Integer codigo;
}
