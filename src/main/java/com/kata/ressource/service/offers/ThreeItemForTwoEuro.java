package com.kata.ressource.service.offers;

import java.math.BigDecimal;
import java.util.List;

import com.kata.entity.CartItem;
import com.kata.entity.OfferStrategy;

public class ThreeItemForTwoEuro implements OfferStrategy {

	@Override
	public BigDecimal OperationStrategy(CartItem cartItem) {
		return BigDecimal.valueOf(2.00);
	}

	@Override
	public BigDecimal OperationStrategy(List<CartItem> cartItem, Integer askedQuantity) {
		return null;
	}

}
