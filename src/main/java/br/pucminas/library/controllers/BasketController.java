package br.pucminas.library.controllers;

import br.pucminas.library.models.Basket;
import br.pucminas.library.models.BasketItem;
import br.pucminas.library.services.BasketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"Basket"})
@RestController
@RequestMapping("/basket")
public class BasketController {
	@Autowired
	private BasketService service;

	@ApiOperation(value = "Mostra o carrinho de compras do usuário.")
	@RequestMapping(path="/{userId}", method=RequestMethod.GET)
	public Basket getBasketFor(
			@ApiParam(required = true, value = "ID do usuário.")
			@Valid
			@PathVariable
			Integer userId) {
		return service.getBasketFor(userId);
	}

	@ApiOperation(value = "Adiciona um item a um carrinho de compras. Caso o carrinho não exista, ele será criado.")
	@RequestMapping(path="/{userId}/items", method=RequestMethod.POST)
	public Basket addItem(
			@ApiParam(required = true, value = "ID do usuário.")
			@Valid
			@PathVariable
			Integer userId,
			@ApiParam(required = true, value = "Objeto do item a ser adicionado.")
			@Valid
			@RequestBody
			BasketItem item) {
		return service.addItem(userId, item);
	}

	@ApiOperation(value = "Deleta um item de um carrinho de compras.")
	@RequestMapping(path="/{userId}/items", method=RequestMethod.DELETE)
	public void removeItem(
			@ApiParam(required = true, value = "ID do usuário.")
			@Valid
			@PathVariable
			Integer userId,
			@ApiParam(required = true, value = "Objeto do item a ser adicionado.")
			@Valid
			@RequestBody
			BasketItem item) {
		service.removeItem(userId, item);
	}

	@ApiOperation(value = "Deleta o carrinho de compras do usuário.")
	@RequestMapping(path="/{userId}", method=RequestMethod.DELETE)
	public void deleteBasketFor(
			@ApiParam(required = true, value = "ID do usuário.")
			@Valid
			@PathVariable
			Integer userId) {
		service.deleteBasketFor(userId);
	}
}
