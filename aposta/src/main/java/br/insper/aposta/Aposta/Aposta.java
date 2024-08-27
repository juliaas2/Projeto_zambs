package br.insper.aposta.Aposta;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.conversions.Bson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
@Getter
@Setter
public class Aposta {

    // private Bson id; --> id do mongo
    @Id
    private String id;

    @NonNull
    private Integer idPartida;

    private LocalDateTime dataAposta;

    private String resultado; // empate, vitoriaMandante, vitoriaVisitante

    private Double valorAposta;

    private String status; // "pendente", "ganhou", "perdeu"
}

