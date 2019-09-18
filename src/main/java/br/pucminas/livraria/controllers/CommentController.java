package br.pucminas.livraria.controllers;

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
import br.pucminas.livraria.dao.Comment;

@RestController
public class CommentController {
	@RequestMapping("/books/{isbn}/comments")
	public List<Comment> comments(@PathVariable("isbn") String isbn) {
		Optional<Book> book = BookController.getBook(isbn);
		
		if(book.isPresent()) {
			return book.get().getComments();			
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	@RequestMapping("/books/{isbn}/comments/{id}")
	public Comment comment(@PathVariable("isbn") String isbn, @PathVariable("id") Integer id) {
		Optional<Book> book = BookController.getBook(isbn);
		
		if(book.isPresent()) {
			Optional<Comment> comment = book
				.get()
				.getComments()
				.stream()
				.filter(c -> c.getId() == id)
				.findFirst();
			
			if(comment.isPresent()) {
				return comment.get();							
			}
		
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found!");
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	@PostMapping("/books/{isbn}/comments")
	public Comment create(@PathVariable("isbn") String isbn, @Valid @RequestBody Comment comment) {
		Optional<Book> book = BookController.getBook(isbn);
		
		if(!book.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
		}
		
		book.get()
			.getComments()
			.add(comment);
		
		return comment;
	}
	
	@PutMapping("/books/{isbn}/comments")
	public Comment update(@PathVariable("isbn") String isbn, @Valid @RequestBody Comment comment) {
		Optional<Book> maybeBook = BookController.getBook(isbn);
		
		if(maybeBook.isPresent()) {
			List<Comment> comments = maybeBook.get().getComments();
			Optional<Comment> oldComment = comments
				.stream()
				.filter(c -> c.getId() == comment.getId())
				.findFirst();
			
			if(oldComment.isPresent()) {
				comment.setCreatedAt(oldComment.get().getCreatedAt());
				comments.remove(oldComment.get());
				comments.add(comment);
				return comment;
			}
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found!");
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	@DeleteMapping("/books/{isbn}/comments/{id}")
	public void delete(@PathVariable("isbn") String isbn, @PathVariable("id") Integer id) {
		Optional<Book> maybeBook = BookController.getBook(isbn);
		
		if(maybeBook.isPresent()) {
			List<Comment> comments = maybeBook.get().getComments();
			
			Optional<Comment> comment = comments
				.stream()
				.filter(c -> c.getId() == id)
				.findFirst();
			
			if(comment.isPresent()) {
				comments.remove(comment.get());
				return;
			}
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found!");
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
}
