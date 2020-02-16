package br.pucminas.library.models;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketItem {
	private String id;
	
	@NotNull
	private Integer quantity;
	
	@NotBlank
	private String isbn;
}
