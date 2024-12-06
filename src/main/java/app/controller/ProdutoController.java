package app.controller;

import app.dto.ProdutoDTO;
import app.dto.ProdutosDTO;
import app.service.ProdutoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoServiceImpl service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public List<List<ProdutoDTO>> createProdutos(@RequestBody List<ProdutosDTO> dtos) {
        return Collections.singletonList(service.createProdutos(dtos));
    }

    @GetMapping
    public List<ProdutoDTO> list() {
        return service.listaProdutos();
    }

    @GetMapping("/{id}")
    public ProdutoDTO get(@PathVariable Long id) {
        return service.getByIdProdutos(id);
    }

    @PutMapping("/update")
    public List<List<ProdutoDTO>> updateProdutos(@RequestBody List<ProdutosDTO> dtos) {
        return Collections.singletonList(service.updateProdutos(dtos));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProdutos(@RequestBody List<Long> ids) {
        service.deleteProdutos(ids);
    }
}
