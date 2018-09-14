package com.kata.utils.ressource.offre;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.ressource.service.offers.ThreeItemForFiveEuro;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ThreeItemForTwoEuroTest {

	@InjectMocks
	private ThreeItemForFiveEuro subject;

	private CartItem source;

	@Before
	public void initialisation() {
		source = new CartItem();
	}

	@Test
	public void shouldReturnTheRightPrice() {
		// given
		source.setAskedQuantity(3);
		Item item = new Item();
		item.setUnitPrice(new BigDecimal(2.85));
		source.setItem(item);
		// when
		BigDecimal actual = subject.OperationStrategy(source);
		// Then
		BigDecimal expected = BigDecimal.valueOf(5.00);
		assertTrue(actual.equals(expected));
	}

}
