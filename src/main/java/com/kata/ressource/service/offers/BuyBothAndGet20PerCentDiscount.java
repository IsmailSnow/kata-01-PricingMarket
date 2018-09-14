package com.kata.ressource.service.offers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.entity.OfferStrategy;

public class BuyBothAndGet20PerCentDiscount implements OfferStrategy {

	@Override
	public BigDecimal OperationStrategy(CartItem cartItem) {
		return null;
	}

	@Override
	public BigDecimal OperationStrategy(List<CartItem> cartItem, Integer askedQuantity) {
		
		List<BigDecimal> list = cartItem.stream()
				.map(CartItem::getItem)
				.map(Item::getUnitPrice)
				.collect(Collectors.toList());
		BigDecimal resultWithoutDiscount = list
				.stream()
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return resultWithoutDiscount
				.multiply(new BigDecimal(askedQuantity))
				.multiply(new BigDecimal(0.8));
	}

}
