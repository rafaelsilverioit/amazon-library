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
import br.pucminas.library.models.Book;
import br.pucminas.library.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Books"})
@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService service;
	
	@ApiOperation(value = "Lista todos os livros do acervo que batem com o filtro passado.")
	@RequestMapping(method=RequestMethod.GET)
	public Pagination<Book> books(
			@ApiParam(required = false, value = "Código ISBN do livro.")
			@RequestParam(required=false, name="isbn")
			String isbn, 
			@ApiParam(required = false, value = "Nome do livro.")
			@RequestParam(required=false, name="name")
			String name,
			@ApiParam(required = false, value = "Descrição do livro.")
			@RequestParam(required=false, name="description")
			String description,
			@ApiParam(required = false, value = "ID do autor do livro.")
			@RequestParam(required=false, name="author")
			Integer author,
			@ApiParam(required = false, value = "Quantidade de livros retornados.", defaultValue = "10")
			@RequestParam(required=false, name="limit", defaultValue = "10")
			Integer limit,
			@ApiParam(required = false, value = "Quantidade de livros a serem pulados.", defaultValue = "0")
			@RequestParam(required=false, name="offset", defaultValue = "0")
			Integer offset
			) {
		Book book = new Book(isbn, name, description, author);
		return service.books(book, limit, offset);
	}
	
	@ApiOperation(value = "Lista um livro identificado pelo seu ISBN.")
	@RequestMapping(path="/{isbn}", method=RequestMethod.GET)
	public Book book(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn) {
		return service.book(isbn);
	}
	
	@ApiOperation(value = "Adiciona um livro ao acervo.")
	@RequestMapping(method=RequestMethod.POST)
	public Book create(
			@ApiParam(required = true, value = "Objeto do livro.")
			@Valid
			@RequestBody
			Book book) {
		return service.create(book);
	}
	
	@ApiOperation(value = "Atualiza um livro do acervo.")
	@RequestMapping(method=RequestMethod.PUT)
	public Book update(
			@ApiParam(required = true, value = "Objeto do livro.")
			@Valid
			@RequestBody
			Book book) {
		return service.update(book);
	}
	
	@ApiOperation(value = "Deleta um livro do acervo, baseado em um ISBN.")
	@RequestMapping(path="/{isbn}", method=RequestMethod.DELETE)
	public void delete(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn) {
		service.delete(isbn);
	}
}
