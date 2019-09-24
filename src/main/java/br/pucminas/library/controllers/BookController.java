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

import br.pucminas.library.dao.Book;
import br.pucminas.library.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Books"})
@RestController
public class BookController {
	@Autowired
	private BookService service;
	
	@ApiOperation(value = "Lista todos os livros do acervo.")
	@RequestMapping("/books")
	public List<Book> books() {
		return service.books();
	}
	
	@ApiOperation(value = "Lista um livro identificado pelo seu ISBN.")
	@RequestMapping("/books/{isbn}")
	public Book book(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn) {
		return service.book(isbn);
	}
	
	@ApiOperation(value = "Adiciona um livro ao acervo.")
	@PostMapping("/books")
	public Book create(
			@ApiParam(required = true, value = "Objeto do livro.")
			@Valid
			@RequestBody
			Book book) {
		return service.create(book);
	}
	
	@ApiOperation(value = "Atualiza um livro do acervo.")
	@PutMapping("/books")
	public Book update(
			@ApiParam(required = true, value = "Objeto do livro.")
			@Valid
			@RequestBody
			Book book) {
		return service.update(book);
	}
	
	@ApiOperation(value = "Deleta um livro do acervo, baseado em um ISBN.")
	@DeleteMapping("/books/{isbn}")
	public void delete(
			@ApiParam(required = true, value = "Código ISBN do livro.")
			@PathVariable("isbn")
			String isbn) {
		service.delete(isbn);
	}
}
