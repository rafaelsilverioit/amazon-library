package br.pucminas.livraria.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Book {
	@NotBlank private String isbn;
	@NotBlank private String name;
	@NotBlank private String description;
	@NotNull private Integer author;
	
	private Date createdAt;
	private List<Comment> comments = new ArrayList<>();
	
	public Book() {}
	
	public Book(String isbn, String name, String description, Integer author) {
		this.isbn = isbn;
		this.name = name;
		this.description = description;
		this.author = author;
		this.createdAt = new Date();
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAuthor() {
		return author;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setAuthor(Integer author) {
		this.author = author;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
