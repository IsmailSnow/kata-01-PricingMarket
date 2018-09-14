package com.kata.utils.ressource.offre;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.ressource.service.offers.BuyTwentyAndGet20PerCentDiscount;

@RunWith(MockitoJUnitRunner.class)
public class BuyTwentyAndGet20PerCentDiscountTest {

	@InjectMocks
	private BuyTwentyAndGet20PerCentDiscount subject;

	private CartItem source;

	@Before
	public void initialisation() {
		source = new CartItem();
	}

	@Test
	public void shouldReturnTheRightPrice() {
		// given
		source.setAskedQuantity(20);
		Item item = new Item();
		item.setUnitPrice(new BigDecimal(9.85));
		source.setItem(item);
		// when
		BigDecimal actual = subject.OperationStrategy(source).round(new MathContext(4, RoundingMode.HALF_UP));
		// Then
		BigDecimal expected = BigDecimal.valueOf(157.6);
		assertTrue(actual.equals(expected));
	}

}
