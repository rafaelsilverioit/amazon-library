package br.pucminas.library.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.pucminas.library.dao.Book;

@Service
public class BookService {
	private final List<Integer> authors = Arrays.asList(new Integer[] {1, 2});
	
	public static final List<Book> books = new ArrayList<>();
	
	public List<Book> books() {
		return books;
	}
	
	public Book book(String isbn) {
		Optional<Book> book = books
			.stream()
			.filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
			.findFirst();
		
		if(book.isPresent()) {
			return book.get();
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	public Book create(Book book) {
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
	
	public Book update(Book book) {
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
	
	public void delete(String isbn) {
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
