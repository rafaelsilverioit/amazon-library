package br.pucminas.library.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel
public class Basket {
	private String id;
	
	@NotNull
	private Integer userId;
	
	@NotNull
	private Date createdAt;
	
	private List<BasketItem> items = new ArrayList<>();

	public Basket() {
	}

	public Basket(String id, Integer userId, Date createdAt) {
		this.id = id;
		this.userId = userId;
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<BasketItem> getItems() {
		return items;
	}

	public void setItems(List<BasketItem> items) {
		this.items = items;
	}
}
