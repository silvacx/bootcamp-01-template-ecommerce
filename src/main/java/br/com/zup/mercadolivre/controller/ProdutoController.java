package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.model.UsuarioLogado;
import br.com.zup.mercadolivre.dto.request.ProdutoRequestDTO;
import br.com.zup.mercadolivre.model.Categoria;
import br.com.zup.mercadolivre.model.Produto;
import br.com.zup.mercadolivre.model.Usuario;
import br.com.zup.mercadolivre.validator.ProibeCaracteristicaIgualValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @PersistenceContext
    EntityManager entityManager;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaIgualValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Produto> criaProduto(@RequestBody @Valid ProdutoRequestDTO request, @AuthenticationPrincipal UsuarioLogado usuarioLogado) {
        Categoria categoria = entityManager.find(Categoria.class, request.getCategoriaId());
        Usuario usuario = usuarioLogado.get();
        Produto produto = request.toModel(categoria, usuario);
        entityManager.persist(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }
}
