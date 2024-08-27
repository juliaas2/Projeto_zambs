package br.insper.aposta.Aposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aposta")
public class ApostaController {

    @Autowired
    private ApostaService apostaService;

    @GetMapping
    public List<Aposta> getApostas( @RequestParam(required = false) String status) {
        return apostaService.listar(status);
    }

    @GetMapping("/{id}")
    public Aposta verificaAposta(@PathVariable String id) {
        return apostaService.verificaAposta(id);
    }

    @PostMapping
    public void salvarAposta(@RequestBody Aposta aposta) {
        apostaService.salvar(aposta);
    }

}