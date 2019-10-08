package br.pucminas.library.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.library.Pagination;
import br.pucminas.library.models.Comment;
import br.pucminas.library.services.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Comments"})
@RestController
@RequestMapping("/books/{isbn}/comments")
public class CommentController {
	@Autowired
	private CommentService service;
	
	@ApiOperation(value = "Lista todos os comentários para um livro.")
	@RequestMapping(method=RequestMethod.GET)
	public Pagination<Comment> comments(
			@ApiParam(required = false, value = "Quantidade de livros retornados.", defaultValue = "10")
			@RequestParam(required=false, name="limit", defaultValue = "10")
			Integer limit,
			@ApiParam(required = false, value = "Quantidade de livros a serem pulados.", defaultValue = "0")
			@RequestParam(required=false, name="offset", defaultValue = "0")
			Integer offset,
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn) {
		return service.comments(isbn, limit, offset);
	}
	
	@ApiOperation(value = "Lista um comentário específico para um livro.")
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public Comment comment(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn,
			@ApiParam(required = true, value = "UUID do livro.")
			@PathVariable("id")
			String id) {
		return service.comment(isbn, id);
	}
	
	@ApiOperation(value = "Adiciona um comentário a um livro do acervo.")
	@RequestMapping(method=RequestMethod.POST)
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
	@RequestMapping(method=RequestMethod.PUT)
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
	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	public void delete(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn,
			@ApiParam(required = true, value = "UUID do comentário.")
			@PathVariable("id")
			String id) {
		service.delete(isbn, id);
	}
}
