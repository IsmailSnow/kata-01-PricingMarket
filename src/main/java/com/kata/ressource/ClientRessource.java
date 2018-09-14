package com.kata.ressource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kata.entity.CartItem;
import com.kata.entity.ShoppingCart;
import com.kata.entity.repository.CartItemRepository;
import com.kata.entity.repository.ShoppingCartRepository;
import com.kata.ressource.service.ClientService;

@RestController
@RequestMapping("shoppingCart")
public class ClientRessource {

	@Autowired
	private CartItemRepository cartItemRepo;

	@Autowired
	private ShoppingCartRepository shoppingCartRepo;

	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "total/{id}")
	public BigDecimal getTotalAmout(@PathVariable String id) {
		ShoppingCart shoppingCart = shoppingCartRepo.findById(Long.valueOf(id)).orElse(new ShoppingCart());
		if (CollectionUtils.isEmpty(shoppingCart.getItems())) {
			throw new IllegalArgumentException("please fill a shopping cart with car item");
		}
		return clientService.getCommandAmout(shoppingCart.getItems().stream().collect(Collectors.toList()));
	}

	@RequestMapping(value = "/all")
	public List<ShoppingCart> getAllShoppingCart() {
		return shoppingCartRepo.findAll();
	}

	@RequestMapping(value = "/test")
	public BigDecimal test() {
		ShoppingCart shoppingCart = new ShoppingCart();
		// 6 coffe // price 1.99 // offer get 5 one free
		CartItem coffe = cartItemRepo.findById(Long.valueOf(10))
				.orElseThrow(() -> new IllegalArgumentException("Coffe is not found"));
		// coffe 4 // price 0.85 // 3 for 2 euro
		CartItem water = cartItemRepo.findById(Long.valueOf(9))
				.orElseThrow(() -> new IllegalArgumentException("Coffe is not found"));
		// Orange 21 // price 2.00 // 20 and dicount of 20%
		CartItem orange = cartItemRepo.findById(Long.valueOf(9))
				.orElseThrow(() -> new IllegalArgumentException("Coffe is not found"));
		// pc 1 // price 200.250 // no offer
		CartItem pc = cartItemRepo.findById(Long.valueOf(16))
				.orElseThrow(() -> new IllegalArgumentException("Coffe is not found"));
		// pc clavier 3 // price 10 // 20% discount
		CartItem clavier = cartItemRepo.findById(Long.valueOf(17))
				.orElseThrow(() -> new IllegalArgumentException("Calvier is not found"));

		shoppingCart.setItems(new HashSet<>(Arrays.asList(coffe, water, orange, pc, clavier)));
		shoppingCartRepo.save(shoppingCart);
		return clientService.getCommandAmout(shoppingCart.getItems().stream().collect(Collectors.toList()));
	}

}
