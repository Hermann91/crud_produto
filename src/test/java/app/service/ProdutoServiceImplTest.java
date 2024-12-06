package app.service;

import app.domain.Produto;
import app.dto.ProdutoDTO;
import app.dto.ProdutosDTO;
import app.error_handling.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceImplTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoServiceImpl produtoServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProdutos() {
        ProdutosDTO produtoDTO = new ProdutosDTO(1L, "Produto 1", 100.0);
        List<ProdutosDTO> produtosDTOList = Collections.singletonList(produtoDTO);

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setName("Produto 1");
        produto.setPrice(100.0);

        when(repository.saveAll(anyList())).thenReturn(Collections.singletonList(produto));

        List<ProdutoDTO> result = produtoServiceImpl.createProdutos(produtosDTOList);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Produto 1", result.get(0).name());
        assertEquals(100.0, result.get(0).price());
    }

    @Test
    void testListaProdutos() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setName("Produto 1");
        produto.setPrice(100.0);

        when(repository.findAll()).thenReturn(Collections.singletonList(produto));

        List<ProdutoDTO> result = produtoServiceImpl.listaProdutos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Produto 1", result.get(0).name());
    }

    @Test
    void testGetByIdProdutos_Found() {

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setName("Produto 1");
        produto.setPrice(100.0);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoDTO result = produtoServiceImpl.getByIdProdutos(1L);
        assertNotNull(result);
        assertEquals("Produto 1", result.name());
        assertEquals(100.0, result.price());
    }

    @Test
    void testGetByIdProdutos_NotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> produtoServiceImpl.getByIdProdutos(1L));
        assertEquals("Produto não encontrado.", exception.getMessage());
    }

    @Test
    void testUpdateProdutos() {
        ProdutosDTO produtoDTO = new ProdutosDTO(1L, "Produto Atualizado", 150.0);
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setName("Produto 1");
        produto.setPrice(100.0);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.saveAll(anyList())).thenReturn(Collections.singletonList(produto));

        List<ProdutoDTO> result = produtoServiceImpl.updateProdutos(Collections.singletonList(produtoDTO));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Produto Atualizado", result.get(0).name());
        assertEquals(150.0, result.get(0).price());
    }

    @Test
    void testDeleteProdutos() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setName("Produto 1");
        produto.setPrice(100.0);

        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        produtoServiceImpl.deleteProdutos(Collections.singletonList(1L));

        verify(repository, times(1)).delete(produto);
    }

    @Test
    void testDeleteProdutos_NotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());


        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> produtoServiceImpl.deleteProdutos(Collections.singletonList(1L)));
        assertEquals("Produto com id 1 não encontrado.", exception.getMessage());
    }
}
