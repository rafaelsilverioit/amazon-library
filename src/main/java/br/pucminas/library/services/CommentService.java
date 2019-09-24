package br.pucminas.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.pucminas.library.dao.Book;
import br.pucminas.library.dao.Comment;

@Service
public class CommentService {
	public List<Comment> comments(String isbn) {
		Optional<Book> book = BookService.getBook(isbn);
		
		if(book.isPresent()) {
			return book.get().getComments();			
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	public Comment comment(String isbn, Integer id) {
		Optional<Book> book = BookService.getBook(isbn);
		
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
	
	public Comment create(String isbn, Comment comment) {
		Optional<Book> book = BookService.getBook(isbn);
		
		if(!book.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
		}
		
		book.get()
			.getComments()
			.add(comment);
		
		return comment;
	}
	
	public Comment update(String isbn, Comment comment) {
		Optional<Book> maybeBook = BookService.getBook(isbn);
		
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
	
	public void delete(String isbn, Integer id) {
		Optional<Book> maybeBook = BookService.getBook(isbn);
		
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
