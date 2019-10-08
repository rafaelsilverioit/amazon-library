package br.pucminas.library.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.pucminas.library.models.Basket;
import br.pucminas.library.models.BasketItem;
import br.pucminas.library.models.Book;

@Service
public class BasketService {
	@Autowired
	private BookService bookService;
	
	public static final List<Basket> baskets = new ArrayList<>();
	
	private final List<Integer> users = Arrays.asList(new Integer[] {1, 2});
	
	public Basket getBasketFor(Integer userId) {
		return findBasketFor(userId);
	}
	
	public Basket addItem(Integer userId, BasketItem item) {
		findUserOrThrowException(userId);
		Optional<Book> book = bookService.getBook(item.getIsbn());
		
		if(!book.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
		}
		
		Basket maybeBasket = findBasketFor(userId);
		
		if(maybeBasket != null) {
			return maybeBasket;
		}
		
		Basket basket = new Basket(UUID.randomUUID().toString(), userId, new Date());
		List<BasketItem> items = basket.getItems();
		
		item.setId(UUID.randomUUID().toString());
		items.add(item);
		
		baskets.add(basket);
		return basket;
	}
	
	public void removeItem(Integer userId, BasketItem item) {
		Basket maybeBasket = findBasketFor(userId);
		
		if(maybeBasket == null) {
			return;
		}
	
		List<BasketItem> items = maybeBasket.getItems();
		
		if(items.contains(item)) {
			items.remove(item);
		}
	}
	
	public void deleteBasketFor(Integer userId) {
		Basket basket = findBasketFor(userId);
		baskets.remove(basket);
	}
	
	private Basket findBasketFor(Integer userId) {
		Optional<Basket> maybeBasket = baskets
			.stream()
			.filter(b -> b.getUserId() == userId)
			.findFirst();
		
		if(maybeBasket.isPresent()) {
			
			return maybeBasket.get();
		}
		
		return null;
	}
	
	private void findUserOrThrowException(Integer userId) {
		Long matches = users
			.stream()
			.filter(u -> u == userId)
			.count();
		
		if(matches.compareTo(1L) != 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
		}
	}
}
