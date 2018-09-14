package com.kata.ressource.service.offers;

import java.math.BigDecimal;
import java.util.List;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.entity.OfferStrategy;

public class BuyFiveItemAndGetOneForFree implements OfferStrategy {

	@Override
	public BigDecimal OperationStrategy(CartItem cartItem) {
		Item item = cartItem.getItem();
		return item.calculTotalWithoutOffer(5);
	}

	@Override
	public BigDecimal OperationStrategy(List<CartItem> cartItem,Integer askedQuantity) {
		return null;
	}

}
