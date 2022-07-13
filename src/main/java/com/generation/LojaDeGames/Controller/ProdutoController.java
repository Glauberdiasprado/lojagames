package com.generation.LojaDeGames.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.LojaDeGames.Repository.ProdutoRepository;
import com.generation.LojaDeGames.model.Produto;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtosRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtosRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> GetById(@PathVariable long id){
		return produtosRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nomeDoProduto/{nomeDoProduto}")
	public ResponseEntity<List<Produto>> GetByNomeDoProduto(@PathVariable String nomeDoProduto){
		return ResponseEntity.ok(produtosRepository.findAllByNomeDoProdutoContainingIgnoreCase(nomeDoProduto));
	}
	
	@PostMapping
	public ResponseEntity<Produto> Post (@Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> PUTCategorias (@RequestBody Produto produto){
		
		return produtosRepository.findById(produto.getId())
				.map(respostas -> ResponseEntity.ok().body(produtosRepository.save(produto)))
				.orElse(ResponseEntity.notFound().build());
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		
		return produtosRepository.findById(id)
			.map(respostas -> {
			produtosRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		})
		.orElse(ResponseEntity.notFound().build());
}
}