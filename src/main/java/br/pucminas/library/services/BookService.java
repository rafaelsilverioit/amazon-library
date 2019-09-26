package br.pucminas.library.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import br.pucminas.library.Pagination;
import br.pucminas.library.models.Book;

@Service
public class BookService {
	private final List<Integer> authors = Arrays.asList(new Integer[] {1, 2});
	
	private final List<Book> books = new ArrayList<>();
	
	public List<Book> books() {
		return books;
	}
	
	public Pagination<Book> books(Book book, Integer limit, Integer offset) {
		List<Book> books = this.books
			.stream()
			.filter(b -> {
				
				String isbn = book.getIsbn();
				String name = book.getName();
				String description = book.getDescription();
				Integer author = book.getAuthor();

				boolean matchesIsbn = !StringUtils.isEmpty(isbn) && b.getIsbn().equalsIgnoreCase(isbn);
				boolean matchesName = !StringUtils.isEmpty(name) && b.getName().equalsIgnoreCase(name);
				boolean matchesDescription = !StringUtils.isEmpty(description) && b.getDescription().equalsIgnoreCase(description);
				boolean matchesAuthor = author != null && b.getAuthor() == author;
				
				return matchesIsbn || matchesName || matchesDescription || matchesAuthor;
			})
			.collect(Collectors.toList());
		
		int newLimit = offset + limit;
		int size = books.size();
		
		List<Book> subList = books.subList(offset, (newLimit <= size ? newLimit : size));
		offset = offset <= size ? offset : size;
		
		Pagination<Book> paginated = new Pagination<Book>(subList, limit, offset);
		return paginated;
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

	public Optional<Book> getBook(String isbn) {
		return books
			.stream()
			.filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
			.findFirst();
	}
}
