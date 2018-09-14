package com.kata.utils;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
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

@RunWith(MockitoJUnitRunner.class)
public class ListToMapConverterTest {

	@InjectMocks
	private ListToMapConverter subject;

	private List<CartItem> source;

	@Before
	public void initialisation() {
		source = new ArrayList<CartItem>();
	}

	@Test
	public void listSoitConvertiEnMapShouldReturnOnlyNone() {
		// Given

		Offer offer = new Offer();
		offer.setOfferType(OfferType.unique);
		offer.setDescription("3 items only for 5 euro");
		offer.setQuantityCondition(3);
		offer.setCode("300");

		Offer offer2 = new Offer();
		offer2.setOfferType(OfferType.unique);
		offer2.setDescription("buy 20 and get 20% dicount");
		offer2.setQuantityCondition(20);
		offer2.setCode("301");
		Offer offer3 = new Offer();
		offer3.setOfferType(OfferType.group);
		offer3.setDescription("buy coffe and water and get 20% dicount");
		offer3.setQuantityCondition(2);
		offer3.setCode("302");
		Offer offer4 = new Offer();
		offer4.setOfferType(OfferType.unique);
		offer4.setDescription("3 items only for 2 euro");
		offer4.setQuantityCondition(3);
		offer4.setCode("303");

		Offer offer5 = new Offer();
		offer5.setOfferType(OfferType.none);
		offer5.setDescription("No offer");
		offer5.setQuantityCondition(1);
		offer5.setCode("304");

		Item item = new Item();
		item.setName("Coffe");
		item.setUnitPrice(new BigDecimal(1.99));
		item.setQuantity(30);
		item.setOffers(new HashSet<>(Arrays.asList(offer, offer5)));

		Item item1 = new Item();
		item1.setName("Water");
		item1.setUnitPrice(new BigDecimal(0.85));
		item1.setQuantity(30);
		item1.setOffers(new HashSet<>(Arrays.asList(offer4, offer5)));

		Item item2 = new Item();
		item2.setName("Orange");
		item2.setUnitPrice(new BigDecimal(5.85));
		item2.setQuantity(30);
		item2.setOffers(new HashSet<>(Arrays.asList(offer5)));

		CartItem cartItem = new CartItem();
		cartItem.setAskedQuantity(1);
		cartItem.setItem(item);

		CartItem cartItem1 = new CartItem();
		cartItem1.setAskedQuantity(1);
		cartItem1.setItem(item1);

		CartItem cartItem2 = new CartItem();
		cartItem2.setAskedQuantity(1);
		cartItem2.setItem(item2);

		source.addAll(Arrays.asList(cartItem, cartItem1, cartItem2));
		Map<OfferType, List<CartItem>> expected = new HashMap<>();
		expected.put(OfferType.none, Arrays.asList(cartItem, cartItem1, cartItem2));
		expected.put(OfferType.unique, Arrays.asList());
		expected.put(OfferType.group, Arrays.asList());

		// when
		Map<OfferType, List<CartItem>> actual = subject.apply(source);
		// Then
		assertTrue(expected.toString().equals(actual.toString()));
	}

	@Test
	public void listSoitConvertiEnMapShouldReturnOnlyUnique() {
		// Given

		Offer offer = new Offer();
		offer.setOfferType(OfferType.unique);
		offer.setDescription("3 items only for 5 euro");
		offer.setQuantityCondition(3);
		offer.setCode("300");

		Offer offer2 = new Offer();
		offer2.setOfferType(OfferType.unique);
		offer2.setDescription("buy 20 and get 20% dicount");
		offer2.setQuantityCondition(20);
		offer2.setCode("301");
		Offer offer3 = new Offer();
		offer3.setOfferType(OfferType.group);
		offer3.setDescription("buy coffe and water and get 20% dicount");
		offer3.setQuantityCondition(2);
		offer3.setCode("302");
		Offer offer4 = new Offer();
		offer4.setOfferType(OfferType.unique);
		offer4.setDescription("3 items only for 2 euro");
		offer4.setQuantityCondition(3);
		offer4.setCode("303");

		Offer offer5 = new Offer();
		offer5.setOfferType(OfferType.none);
		offer5.setDescription("No offer");
		offer5.setQuantityCondition(1);
		offer5.setCode("304");

		Item item = new Item();
		item.setName("Coffe");
		item.setUnitPrice(new BigDecimal(1.99));
		item.setQuantity(30);
		item.setOffers(new HashSet<>(Arrays.asList(offer, offer5)));

		Item item1 = new Item();
		item1.setName("Water");
		item1.setUnitPrice(new BigDecimal(0.85));
		item1.setQuantity(30);
		item1.setOffers(new HashSet<>(Arrays.asList(offer4, offer5)));

		Item item2 = new Item();
		item2.setName("Orange");
		item2.setUnitPrice(new BigDecimal(5.85));
		item2.setQuantity(30);
		item2.setOffers(new HashSet<>(Arrays.asList(offer2, offer5)));

		CartItem cartItem = new CartItem();
		cartItem.setAskedQuantity(5);
		cartItem.setItem(item);

		CartItem cartItem1 = new CartItem();
		cartItem1.setAskedQuantity(6);
		cartItem1.setItem(item1);

		CartItem cartItem2 = new CartItem();
		cartItem2.setAskedQuantity(21);
		cartItem2.setItem(item2);

		source.addAll(Arrays.asList(cartItem, cartItem1, cartItem2));
		Map<OfferType, List<CartItem>> expected = new HashMap<>();
		expected.put(OfferType.none, Arrays.asList());
		expected.put(OfferType.unique, Arrays.asList(cartItem, cartItem1, cartItem2));
		expected.put(OfferType.group, Arrays.asList());

		// when
		Map<OfferType, List<CartItem>> actual = subject.apply(source);
		// Then
		assertTrue(expected.toString().equals(actual.toString()));
	}

	@Test
	public void listSoitConvertiEnMapShouldReturnTwoGroupAndOneUnique() {
		// Given

		Offer offer = new Offer();
		offer.setOfferType(OfferType.unique);
		offer.setDescription("3 items only for 5 euro");
		offer.setQuantityCondition(3);
		offer.setCode("300");

		Offer offer2 = new Offer();
		offer2.setOfferType(OfferType.unique);
		offer2.setDescription("buy 20 and get 20% dicount");
		offer2.setQuantityCondition(20);
		offer2.setCode("301");
		Offer offer3 = new Offer();
		offer3.setOfferType(OfferType.group);
		offer3.setDescription("buy coffe and water and get 20% dicount");
		offer3.setQuantityCondition(2);
		offer3.setCode("302");
		Offer offer4 = new Offer();
		offer4.setOfferType(OfferType.unique);
		offer4.setDescription("3 items only for 2 euro");
		offer4.setQuantityCondition(3);
		offer4.setCode("303");

		Offer offer5 = new Offer();
		offer5.setOfferType(OfferType.none);
		offer5.setDescription("No offer");
		offer5.setQuantityCondition(1);
		offer5.setCode("304");

		Item item = new Item();
		item.setName("Coffe");
		item.setUnitPrice(new BigDecimal(1.99));
		item.setQuantity(30);
		item.setOffers(new HashSet<>(Arrays.asList(offer, offer2, offer3, offer5)));

		Item item1 = new Item();
		item1.setName("Water");
		item1.setUnitPrice(new BigDecimal(0.85));
		item1.setQuantity(30);
		item1.setOffers(new HashSet<>(Arrays.asList(offer, offer2, offer3, offer5)));

		Item item2 = new Item();
		item2.setName("Orange");
		item2.setUnitPrice(new BigDecimal(5.85));
		item2.setQuantity(30);
		item2.setOffers(new HashSet<>(Arrays.asList(offer2, offer5)));

		CartItem cartItem = new CartItem();
		cartItem.setAskedQuantity(1);
		cartItem.setItem(item);

		CartItem cartItem1 = new CartItem();
		cartItem1.setAskedQuantity(1);
		cartItem1.setItem(item1);

		CartItem cartItem2 = new CartItem();
		cartItem2.setAskedQuantity(21);
		cartItem2.setItem(item2);

		source.addAll(Arrays.asList(cartItem, cartItem1, cartItem2));
		Map<OfferType, List<CartItem>> expected = new HashMap<>();
		expected.put(OfferType.none, Arrays.asList());
		expected.put(OfferType.unique, Arrays.asList(cartItem2));
		expected.put(OfferType.group, Arrays.asList(cartItem, cartItem1));

		// when
		Map<OfferType, List<CartItem>> actual = subject.apply(source);
		// Then
		assertTrue(expected.toString().equals(actual.toString()));
	}

	@Test
	public void listSoitConvertiEnMap() {

		// Given

		Offer offer = new Offer();
		offer.setOfferType(OfferType.unique);
		offer.setDescription("3 items only for 5 euro");
		offer.setQuantityCondition(3);
		offer.setCode("300");

		Offer offer2 = new Offer();
		offer2.setOfferType(OfferType.unique);
		offer2.setDescription("buy 20 and get 20% dicount");
		offer2.setQuantityCondition(20);
		offer2.setCode("301");
		Offer offer3 = new Offer();
		offer3.setOfferType(OfferType.group);
		offer3.setDescription("buy coffe and water and get 20% dicount");
		offer3.setQuantityCondition(2);
		offer3.setCode("302");
		Offer offer4 = new Offer();
		offer4.setOfferType(OfferType.unique);
		offer4.setDescription("3 items only for 2 euro");
		offer4.setQuantityCondition(3);
		offer4.setCode("303");

		Offer offer5 = new Offer();
		offer5.setOfferType(OfferType.none);
		offer5.setDescription("No offer");
		offer5.setQuantityCondition(1);
		offer5.setCode("304");

		Item item = new Item();
		item.setName("Coffe");
		item.setUnitPrice(new BigDecimal(1.99));
		item.setQuantity(30);
		item.setOffers(new HashSet<>(Arrays.asList(offer, offer5)));

		Item item1 = new Item();
		item1.setName("Water");
		item1.setUnitPrice(new BigDecimal(0.85));
		item1.setQuantity(30);
		item1.setOffers(new HashSet<>(Arrays.asList(offer4, offer5)));

		Item item2 = new Item();
		item2.setName("Orange");
		item2.setUnitPrice(new BigDecimal(5.85));
		item2.setQuantity(30);
		item2.setOffers(new HashSet<>(Arrays.asList(offer2)));

		CartItem cartItem = new CartItem();
		cartItem.setAskedQuantity(1);
		cartItem.setItem(item);

		CartItem cartItem1 = new CartItem();
		cartItem1.setAskedQuantity(1);
		cartItem1.setItem(item1);

		CartItem cartItem2 = new CartItem();
		cartItem2.setAskedQuantity(21);
		cartItem2.setItem(item2);

		source.addAll(Arrays.asList(cartItem, cartItem1, cartItem2));
		Map<OfferType, List<CartItem>> expected = new HashMap<>();
		expected.put(OfferType.none, Arrays.asList(cartItem, cartItem1));
		expected.put(OfferType.unique, Arrays.asList(cartItem2));
		expected.put(OfferType.group, Arrays.asList());

		// when
		Map<OfferType, List<CartItem>> actual = subject.apply(source);
		// Then
		assertTrue(expected.toString().equals(actual.toString()));

	}

}
