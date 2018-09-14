package com.kata;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kata.entity.Item;
import com.kata.entity.Offer;
import com.kata.entity.OfferType;
import com.kata.entity.ShoppingCart;
import com.kata.entity.repository.CartItemRepository;
import com.kata.entity.repository.ItemRepository;
import com.kata.entity.repository.OfferRepository;
import com.kata.entity.repository.ShoppingCartRepository;

@SpringBootApplication
public class Kata1Application implements CommandLineRunner {

	@Autowired
	private ItemRepository itemRepo;

	@Autowired
	private CartItemRepository cartItemRepo;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	public static void main(String[] args) {
		SpringApplication.run(Kata1Application.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		ShoppingCart shoppingCart = new ShoppingCart();

//		Offer offer = new Offer();
//		offer.setOfferType(OfferType.unique);
//		offer.setDescription("3 items only for 5 euro");
//
//		Offer offer1 = new Offer();
//		offer1.setOfferType(OfferType.unique);
//		offer1.setDescription("buy 5 items and get one for free");
//
//		Offer offer2 = new Offer();
//		offer2.setOfferType(OfferType.unique);
//		offer2.setDescription("buy 20 and get 20% dicount");
//
//		Offer offer3 = new Offer();
//		offer3.setOfferType(OfferType.group);
//		offer3.setDescription("buy coffe and water and get 20% dicount");
//
//		Offer offer4 = new Offer();
//		offer4.setOfferType(OfferType.unique);
//		offer4.setDescription("3 items only for 2 euro");

//		Item item = new Item();
//		item.setName("Coffe");
//		item.setUnitPrice(new BigDecimal(1.99));
//		item.setQuantity(30);
//		item.setOffers(new HashSet<>(Arrays.asList(offer, offer1, offer2, offer3)));
//
//		Item item1 = new Item();
//		item1.setName("Water");
//		item1.setUnitPrice(new BigDecimal(0.85));
//		item1.setQuantity(30);
//		item1.setOffers(new HashSet<>(Arrays.asList(offer4, offer1, offer2, offer3)));
//
//		CartItem cartItem = new CartItem();
//		cartItem.setAskedQuantity(6);
//		cartItem.setItem(item);
//		cartItem.setShoppingCart(shoppingCart);
//
//		CartItem cartItem1 = new CartItem();
//		cartItem1.setAskedQuantity(4);
//		cartItem1.setItem(item1);
//		cartItem1.setShoppingCart(shoppingCart);
//		itemRepo.save(item);
//		itemRepo.save(item1);
//		offerRepository.save(offer);
//		offerRepository.save(offer1);
//		offerRepository.save(offer2);
//		offerRepository.save(offer3);
//		offerRepository.save(offer4);
//
//		shoppingCart.setItems(new HashSet<>(Arrays.asList(cartItem, cartItem1)));
//
//		shoppingCartRepository.save(shoppingCart);
//
//		Item item = new Item();
//		item.setName("Kinder");
//		item.setUnitPrice(new BigDecimal(1.99));
//		item.setQuantity(30);
//
//		Item item1 = new Item();
//		item1.setName("Ipad");
//		item1.setUnitPrice(new BigDecimal(190.99));
//		item1.setQuantity(30);
//
//		CartItem cartItem = new CartItem();
//		cartItem.setAskedQuantity(5);
//		cartItem.setItem(item);
//		cartItem.setShoppingCart(shoppingCartRepository.findById(Long.valueOf(8)).get());
//
//		CartItem cartItem1 = new CartItem();
//		cartItem1.setAskedQuantity(2);
//		cartItem1.setItem(item1);
//		cartItem1.setShoppingCart(shoppingCartRepository.findById(Long.valueOf(8)).get());
//		itemRepo.save(item);
//		itemRepo.save(item1);
//		shoppingCartRepository.findById(Long.valueOf(8)).get()
//				.setItems(new HashSet<>(Arrays.asList(cartItem, cartItem1)));
//
//		shoppingCartRepository.save(shoppingCartRepository.findById(Long.valueOf(8)).get());

	}
}
