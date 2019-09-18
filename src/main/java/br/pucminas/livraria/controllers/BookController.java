package br.pucminas.livraria.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucminas.livraria.dao.Book;

@RestController
public class BookController {
	public static final List<Book> books = new ArrayList<>();
	private final List<Integer> authors = Arrays.asList(new Integer[] {1, 2});
	
	@RequestMapping("/books")
	public List<Book> books() {
		return books;
	}
	
	@RequestMapping("/books/{isbn}")
	public Book book(@PathVariable("isbn") String isbn) {
		Optional<Book> book = books
			.stream()
			.filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
			.findFirst();
		
		if(book.isPresent()) {
			return book.get();
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	@PostMapping("/books")
	public Book create(@Valid @RequestBody Book book) {
		Optional<Book> maybeBook = getBook(book);
		
		if(maybeBook.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Book already added!");
		}
		
		if(authors.contains(book.getAuthor())) {
			book.setCreatedAt(new Date());
			books.add(book);
			return book;
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found!");
	}
	
	@PutMapping("/books")
	public Book update(@Valid @RequestBody Book book) {
		Optional<Book> maybeBook = getBook(book);
		
		if(maybeBook.isPresent() && authors.contains(book.getAuthor())) {
			Book oldBook = maybeBook.get();
			book.setCreatedAt(oldBook.getCreatedAt());
			books.remove(oldBook);
			books.add(book);
			return book;			
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book or author not found!");
	}
	
	@DeleteMapping("/books/{isbn}")
	public void delete(@PathVariable("isbn") String isbn) {
		Optional<Book> maybeBook = getBook(isbn);
		
		if(maybeBook.isPresent()) {
			Book oldBook = maybeBook.get();
			books.remove(oldBook);
			return;
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	private Optional<Book> getBook(Book book) {
		String isbn = book.getIsbn();
		return getBook(isbn);
	}

	public static Optional<Book> getBook(String isbn) {
		return books
			.stream()
			.filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
			.findFirst();
	}
}
