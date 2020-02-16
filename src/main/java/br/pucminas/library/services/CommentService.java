package br.pucminas.library.services;

import br.pucminas.library.Pagination;
import br.pucminas.library.models.Book;
import br.pucminas.library.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
	@Autowired
	private BookService bookService;
	
	public Pagination<Comment> comments(String isbn, Integer limit, Integer offset) {
		Optional<Book> book = bookService.getBook(isbn);
		
		if(book.isPresent()) {
			int newLimit = offset + limit;
			
			List<Comment> comments = book.get().getComments();
			int size = comments.size();
			
			List<Comment> subList = comments.subList(offset, (Math.min(newLimit, size)));
			offset = offset <= size ? offset : size;
			
			return new Pagination<Comment>(subList, limit, offset);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	public Comment comment(String isbn, String id) {
		Optional<Book> book = bookService.getBook(isbn);
		
		if(book.isPresent()) {
			Optional<Comment> comment = book
				.get()
				.getComments()
				.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst();
			
			if(comment.isPresent()) {
				return comment.get();							
			}
		
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found!");
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	public Comment create(String isbn, Comment comment) {
		Optional<Book> book = bookService.getBook(isbn);
		
		if(!book.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
		}
		
		List<Comment> comments = book.get().getComments();
		comment.setId(UUID.randomUUID().toString());
		comment.setCreatedAt(new Date());
		comments.add(comment);
		
		return comment;
	}
	
	public Comment update(String isbn, Comment comment) {
		Optional<Book> maybeBook = bookService.getBook(isbn);
		
		if(maybeBook.isPresent()) {
			List<Comment> comments = maybeBook.get().getComments();
			Optional<Comment> oldComment = comments
				.stream()
				.filter(c -> c.getId().equals(comment.getId()))
				.findFirst();
			
			if(oldComment.isPresent()) {
				Comment tmp = oldComment.get();
				comment.setCreatedAt(tmp.getCreatedAt());
				comment.setId(tmp.getId());
				
				comments.remove(tmp);
				comments.add(comment);
				return comment;
			}
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found!");
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
	}
	
	public void delete(String isbn, String id) {
		Optional<Book> maybeBook = bookService.getBook(isbn);
		
		if(maybeBook.isPresent()) {
			List<Comment> comments = maybeBook.get().getComments();
			
			Optional<Comment> comment = comments
				.stream()
				.filter(c -> c.getId().equals(id))
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
