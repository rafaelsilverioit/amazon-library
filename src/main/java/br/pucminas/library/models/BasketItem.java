package br.pucminas.library.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel
public class BasketItem {
	private Integer id;
	
	@NotNull
	private Integer quantity;
	
	@NotBlank
	private String isbn;
	
	public BasketItem() {
	}
	
	public BasketItem(Integer id, String isbn, Integer quantity) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
