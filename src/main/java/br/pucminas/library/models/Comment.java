package br.pucminas.library.models;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	private String id;

	@NotNull
	private Integer userId;

	@NotBlank
	private String content;

	private Date createdAt;

	public Comment(String id, Integer userId, String content) {
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.createdAt = new Date();
	}
}
