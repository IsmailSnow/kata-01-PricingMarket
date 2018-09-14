package com.kata.entity;

import java.math.BigDecimal;

/*********Interface that will be called by item to define stratgy to apply for none,uniqueand group offer***********/
import java.util.List;

public interface OfferStrategy {
	public BigDecimal OperationStrategy(CartItem cartItem);
	public BigDecimal OperationStrategy(List<CartItem> cartItem ,Integer askedQuantity);
}
