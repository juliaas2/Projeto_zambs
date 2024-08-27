package br.insper.aposta.Aposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class ApostaService {

    @Autowired
    private ApostaRepository apostaRepository;

    public void salvar(Aposta aposta) {
        aposta.setId(UUID.randomUUID().toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RetornarPartidaDTO> partida = restTemplate.getForEntity("http://localhost:8080/partida/" + aposta.getIdPartida(), RetornarPartidaDTO.class);

        if (partida.getStatusCode().is2xxSuccessful()) {

            if (partida.getBody().getStatus().equals("AGENDADA")) {
                aposta.setStatus("PENDENTE");
                apostaRepository.save(aposta);
            }
            else {
                throw new PartidaInvalidaException();
            }
        }
        else {
            throw new PartidaNaoEncontradaException(aposta.getIdPartida());
        }
    }
    public Aposta verificaAposta(String id) {

        Aposta aposta = apostaRepository.findById(id).orElse(null);

        if (aposta != null) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<RetornarPartidaDTO> partida = restTemplate.getForEntity("http://localhost:8080/partida/" + aposta.getIdPartida(), RetornarPartidaDTO.class);

            if (partida.getStatusCode().is2xxSuccessful()) {
                if (partida.getBody().getStatus().equals("REALIZADA")) {
                    if (partida.getBody().getPlacarMandante() > partida.getBody().getPlacarVisitante()) {
                        if (aposta.getResultado().equals("VITORIA_MANDANTE")) {
                            aposta.setStatus("GANHOU");
                        } else {
                            aposta.setStatus("PERDEU");
                        }
                    } else if (partida.getBody().getPlacarMandante() < partida.getBody().getPlacarVisitante()) {
                        if (aposta.getResultado().equals("VITORIA_VISITANTE")) {
                            aposta.setStatus("GANHOU");
                        } else {
                            aposta.setStatus("PERDEU");
                        }
                    } else {
                        if (aposta.getResultado().equals("EMPATE")) {
                            aposta.setStatus("GANHOU");
                        } else {
                            aposta.setStatus("PERDEU");
                        }
                    }
                    apostaRepository.save(aposta);
                }
            }
            else {
                throw new PartidaNaoEncontradaException(aposta.getIdPartida());
            }
        }

        return aposta;
    }
    public List<Aposta> listar(String status){

        List<Aposta> apostas = apostaRepository.findAll();
        if (status != null) {
            apostas = apostaRepository.findByStatus(status);
        }
        return apostas;
    }
}