package br.pucminas.library.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.library.dao.Comment;
import br.pucminas.library.services.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Comments"})
@RestController
public class CommentController {
	@Autowired
	private CommentService service;
	
	@ApiOperation(value = "Lista todos os comentários para um livro.")
	@RequestMapping("/books/{isbn}/comments")
	public List<Comment> comments(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn) {
		return service.comments(isbn);
	}
	
	@ApiOperation(value = "Lista um comentário específico para um livro.")
	@RequestMapping("/books/{isbn}/comments/{id}")
	public Comment comment(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn,
			@ApiParam(required = true, value = "ID do livro.")
			@PathVariable("id")
			Integer id) {
		return service.comment(isbn, id);
	}
	
	@ApiOperation(value = "Adiciona um comentário a um livro do acervo.")
	@PostMapping("/books/{isbn}/comments")
	public Comment create(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn,
			@ApiParam(required = true, value = "Objeto de comentário.")
			@Valid
			@RequestBody
			Comment comment) {
		return service.create(isbn, comment);
	}
	
	@ApiOperation(value = "Atualiza um comentário de um livro do acervo.")
	@PutMapping("/books/{isbn}/comments")
	public Comment update(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn,
			@ApiParam(required = true, value = "Objeto de comentário.")
			@Valid
			@RequestBody
			Comment comment) {
		return service.update(isbn, comment);
	}
	
	@ApiOperation(value = "Apaga um comentário de um livro do acervo.")
	@DeleteMapping("/books/{isbn}/comments/{id}")
	public void delete(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn,
			@ApiParam(required = true, value = "ID do comentário.")
			@PathVariable("id")
			Integer id) {
		service.delete(isbn, id);
	}
}
