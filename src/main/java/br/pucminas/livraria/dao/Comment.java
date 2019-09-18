package br.pucminas.livraria.dao;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Comment {
	@NotNull private Integer id;
	@NotNull private Integer userId;
	@NotBlank private String content;
	
	private Date createdAt;
	
	public Comment() {}
	
	public Comment(Integer id, Integer userId, String content) {
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.createdAt = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
