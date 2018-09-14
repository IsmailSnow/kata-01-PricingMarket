package com.kata.utils.ressource.impl;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.entity.Offer;
import com.kata.entity.OfferType;
import com.kata.ressource.service.ChainCalculAndUpdate;
import com.kata.ressource.service.impl.TraitementMultipleOffre;
import com.kata.ressource.service.impl.TraitementUniqueOffre;
import com.kata.ressource.service.impl.TraitementWithoutOffre;

@RunWith(MockitoJUnitRunner.class)
public class TraitementUniqueOffreTest {

	@InjectMocks
	private TraitementUniqueOffre subject;

	private Map<OfferType, List<CartItem>> source;

	@Before
	public void initialisation() {
		source = new HashMap<>();
	}

	@Test
	public void shouldReturnTheRightPrice() {

		Offer offer5 = new Offer();
		offer5.setOfferType(OfferType.none);
		offer5.setDescription("No offer");
		offer5.setQuantityCondition(1);

		Offer offer4 = new Offer();
		offer4.setOfferType(OfferType.unique);
		offer4.setQuantityCondition(3);
		offer4.setCode(String.valueOf("101"));

		Offer offer3 = new Offer();
		offer3.setOfferType(OfferType.unique);
		offer3.setQuantityCondition(3);
		offer3.setCode(String.valueOf("105"));

		// given
		Item item = new Item();
		item.setUnitPrice(new BigDecimal(9.85));
		item.setOffers(new HashSet<>(Arrays.asList(offer5)));
		item.setName("test");
		item.setQuantity(30);

		Item item1 = new Item();
		item1.setUnitPrice(new BigDecimal(18.85));
		item1.setOffers(new HashSet<>(Arrays.asList(offer4)));
		item1.setName("test1");
		item1.setQuantity(30);
		Item item2 = new Item();
		item2.setUnitPrice(new BigDecimal(0.85));
		item2.setOffers(new HashSet<>(Arrays.asList(offer3)));
		item2.setName("test2");
		item2.setQuantity(30);

		CartItem cartItem1 = new CartItem();
		cartItem1.setAskedQuantity(2);
		cartItem1.setItem(item);
		CartItem cartItem2 = new CartItem();
		cartItem2.setAskedQuantity(6);
		cartItem2.setItem(item1);
		CartItem cartItem3 = new CartItem();
		cartItem3.setAskedQuantity(4);
		cartItem3.setItem(item2);

		source.put(OfferType.none, Arrays.asList(cartItem1));
		source.put(OfferType.unique, Arrays.asList(cartItem2, cartItem3));
		source.put(OfferType.group, Arrays.asList());

		// when
		BigDecimal totalCost = BigDecimal.ZERO;
		ChainCalculAndUpdate chain = new TraitementWithoutOffre();
		ChainCalculAndUpdate chain1 = subject;
		ChainCalculAndUpdate chain2 = new TraitementMultipleOffre();
		chain.setNextOperation(chain1);
		chain1.setNextOperation(chain2);
		BigDecimal actual = chain.calculateAmout(source, totalCost).round(new MathContext(4, RoundingMode.HALF_UP));
		// Then
		BigDecimal expected = new BigDecimal(116.80).round(new MathContext(4, RoundingMode.HALF_UP));
		assertTrue(actual.equals(expected));
	}

}
