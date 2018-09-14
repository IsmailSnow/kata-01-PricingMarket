package com.kata.ressource.service.offers;

import java.math.BigDecimal;
import java.util.List;

import com.kata.entity.CartItem;
import com.kata.entity.OfferStrategy;

public class BuyTwoDifferentTogetherFor6Euro implements OfferStrategy{

	@Override
	public BigDecimal OperationStrategy(CartItem cartItem) {
		return null;
	}

	@Override
	public BigDecimal OperationStrategy(List<CartItem> cartItem,Integer askedQuantity) {
		BigDecimal itemCost = BigDecimal.ZERO;
		BigDecimal totalCost = BigDecimal.ZERO;
		itemCost = BigDecimal.valueOf(6.00).multiply(new BigDecimal(askedQuantity));
		totalCost = totalCost.add(itemCost);
		return totalCost;
	}

}
