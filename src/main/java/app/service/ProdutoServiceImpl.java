package app.service;

import app.domain.Produto;
import app.dto.ProdutosDTO;
import app.dto.ProdutoDTO;
import app.error_handling.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl {

    private final ProdutoRepository repository;

    public List<ProdutoDTO> createProdutos(List<ProdutosDTO> dtos) {
        List<Produto> produtos = dtos.stream()
                .map(dto -> {
                    Produto produto = new Produto();
                    produto.setName(dto.name());
                    produto.setPrice(dto.price());
                    return produto;
                })
                .toList();

        List<Produto> savedProdutos = repository.saveAll(produtos);

        return savedProdutos.stream()
                .map(p -> new ProdutoDTO(p.getId(), p.getName(), p.getPrice()))
                .toList();
    }

    public List<ProdutoDTO> listaProdutos() {
        return repository.findAll()
                .stream()
                .map(p -> new ProdutoDTO(p.getId(), p.getName(), p.getPrice()))
                .toList();
    }

    public ProdutoDTO getByIdProdutos(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        return new ProdutoDTO(produto.getId(), produto.getName(), produto.getPrice());
    }

    public List<ProdutoDTO> updateProdutos(List<ProdutosDTO> dtos) {
        List<Produto> produtos = dtos.stream()
                .map(dto -> {
                    Produto produto = repository.findById(dto.id())
                            .orElseThrow(() -> new ResourceNotFoundException("Produto com id " + dto.id() + " não encontrado."));
                    produto.setName(dto.name());
                    produto.setPrice(dto.price());
                    return produto;
                })
                .collect(Collectors.toList());

        List<Produto> updatedProdutos = repository.saveAll(produtos);

        return updatedProdutos.stream()
                .map(p -> new ProdutoDTO(p.getId(), p.getName(), p.getPrice()))
                .collect(Collectors.toList());
    }


    public void deleteProdutos(List<Long> ids) {
        ids.forEach(id -> {
            Optional<Produto> optionalProduto = repository.findById(id);

            if (optionalProduto.isEmpty()) {
                throw new ResourceNotFoundException("Produto com id " + id + " não encontrado.");
            }

            repository.delete(optionalProduto.get());
        });
    }


}
