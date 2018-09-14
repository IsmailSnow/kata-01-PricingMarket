package com.kata.utils.ressource.offre;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.ressource.service.offers.BuyTwoDifferentTogetherFor6Euro;

@RunWith(MockitoJUnitRunner.class)
public class BuyTwoDifferentTogetherFor6EuroTest {

	@InjectMocks
	private BuyTwoDifferentTogetherFor6Euro subject;

	private List<CartItem> source;

	@Before
	public void initialisation() {
		source = new ArrayList<>();
	}

	@Test
	public void shouldReturnTheRightPrice() {
		// given
		Item item = new Item();
		item.setUnitPrice(new BigDecimal(4.50));

		Item item1 = new Item();
		item1.setUnitPrice(new BigDecimal(2.50));

		CartItem cartItem1 = new CartItem();
		cartItem1.setAskedQuantity(3);
		cartItem1.setItem(item);
		CartItem cartItem2 = new CartItem();
		cartItem2.setAskedQuantity(3);
		cartItem2.setItem(item1);

		source.addAll(Arrays.asList(cartItem1, cartItem2));
		// when
		BigDecimal actual = subject.OperationStrategy(source, 3).round(new MathContext(4, RoundingMode.HALF_UP));
		// Then
		BigDecimal expected = BigDecimal.valueOf(18.00);
		assertTrue(actual.equals(expected));
	}

}
