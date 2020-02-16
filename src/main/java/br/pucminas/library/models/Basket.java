package br.pucminas.library.models;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
	private String id;
	
	@NotNull
	private Integer userId;
	
	@NotNull
	private Date createdAt;
	
	@Singular
	private List<BasketItem> items = new ArrayList<>();

	public Basket(String id, Integer userId, Date createdAt) {
		this.id = id;
		this.userId = userId;
		this.createdAt = createdAt;
	}
}
