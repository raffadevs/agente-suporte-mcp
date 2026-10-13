package dev.raffadevs.chamados.artigo;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artigos")
public class ArtigoController {

    private final BuscaArtigoService buscaArtigoService;

    public ArtigoController(BuscaArtigoService buscaArtigoService) {
        this.buscaArtigoService = buscaArtigoService;
    }

    @GetMapping("/busca")
    public List<ArtigoEncontrado> buscar(@RequestParam String q) {
        return buscaArtigoService.buscar(q);
    }
}
