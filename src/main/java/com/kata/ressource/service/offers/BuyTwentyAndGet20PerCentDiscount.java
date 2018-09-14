package com.kata.ressource.service.offers;

import java.math.BigDecimal;
import java.util.List;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.entity.OfferStrategy;

public class BuyTwentyAndGet20PerCentDiscount implements OfferStrategy {

	@Override
	public BigDecimal OperationStrategy(CartItem cartItem) {
		Item item = cartItem.getItem();
		BigDecimal itemCost = BigDecimal.ZERO;
		BigDecimal totalCost = BigDecimal.ZERO;
		itemCost = item.calculTotalWithoutOffer(20).multiply(new BigDecimal(0.8));
		totalCost = totalCost.add(itemCost);
		return totalCost;
	}

	@Override
	public BigDecimal OperationStrategy(List<CartItem> cartItem,Integer askedQuantity) {
		return null;
	}

}
