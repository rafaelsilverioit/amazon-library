package br.pucminas.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@NotBlank
	private String isbn;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotNull
	private Integer author;

	private Date createdAt;

	@JsonIgnore
	@Singular
	private List<Comment> comments = new ArrayList<>();

	public Book(String isbn, String name, String description, Integer author) {
		this.isbn = isbn;
		this.name = name;
		this.description = description;
		this.author = author;
		this.createdAt = new Date();
	}
}
