package com.kata.ressource.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.entity.Offer;
import com.kata.entity.OfferStrategy;
import com.kata.entity.OfferType;
import com.kata.ressource.service.ChainCalculAndUpdate;
import com.kata.utils.StrategyDefiner;

public class TraitementMultipleOffre implements ChainCalculAndUpdate {

	private final static Logger logger = LoggerFactory.getLogger(TraitementMultipleOffre.class);

	private ChainCalculAndUpdate nextInChain;

	@Override
	public void setNextOperation(ChainCalculAndUpdate nextInChain) {
		this.nextInChain = nextInChain;

	}

	@Override
	public BigDecimal calculateAmout(Map<OfferType, List<CartItem>> map, BigDecimal total) {
		List<CartItem> list = map.get(OfferType.group);
		if (CollectionUtils.isEmpty(list))
			return BigDecimal.ZERO;
		logger.info("Operation started for grouped offres");
		logger.info("Operation started for grouped offres");
		List<Offer> offers = new ArrayList<>();
		//Recuperation des tous les offres groupe
		for (CartItem cartItemm : list) {
			List<Offer> offerss = cartItemm.getItem().getOffers().stream()
					.filter(c -> c.getOfferType().equals(OfferType.group)).collect(Collectors.toList());
			offers.addAll(offerss);
		}
		Set<Offer> offersFiltred = new HashSet<>(offers);
		offers.clear();
		offers.addAll(offersFiltred);

		logger.info("recuperation de tous les group offersuccess");
		Map<Offer, List<CartItem>> result = new HashMap<>();
		logger.info("Consctruction d'une map (Offer,List<CartItem>) pour qu on puisse appliquer les offres sur les cartItems qui ont la meme offre");
		for (Offer offer : offers) {
			List<CartItem> listCartItems = list.stream().filter(a -> a.getItem().getOffers().contains(offer))
					.collect(Collectors.toList());
			result.put(offer, listCartItems);
		}
		logger.info("Construction success");
		logger.info("Traitement de la map");
		BigDecimal costToTaL = BigDecimal.ZERO;
		for (Offer offer : offers) {
			List<CartItem> cartItems = result.get(offer);
			costToTaL = costToTaL.add(calcultAndUpdateItem(cartItems, offer));
		}
		logger.info("item with multiple offer cost " + costToTaL);
		return costToTaL;
	}

	private BigDecimal calcultAndUpdateItem(List<CartItem> cartItems, Offer offer) {

		// pour les elements qui ont la meme offre , on recupere la quantité minimale 
		List<Integer> list = cartItems.stream().map(CartItem::getAskedQuantity).collect(Collectors.toList());
		int minimumAskedQuantity = list.stream().mapToInt(a -> a).min().orElse(0);
		OfferStrategy offerStrategy = StrategyDefiner.defineStrategyForMultipleOffer(offer.getCode(),
				minimumAskedQuantity);
		Item item = cartItems.stream().filter(a -> a.getAskedQuantity().equals(minimumAskedQuantity))
				.collect(Collectors.toList()).get(0).getItem();
		item.setOfferStrategy(offerStrategy);
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(item.OperationStrategy(cartItems, minimumAskedQuantity));
		logger.info("Offer group applied with success");// car on recupere le minimum de la liste
		logger.info("Traitement des elements qui restent");
		// vu qu on a recuperer que le minimum il reste des elements 
		// pour ses elements l offre n est plus caplicable 
		List<CartItem> rest = cartItems.stream().filter(a -> a.getAskedQuantity() != minimumAskedQuantity)
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(rest)) {
			for (CartItem cart : rest) {
				total = total.add(cart.getItem()
						.calculTotalWithoutOffer(Math.subtractExact(cart.getAskedQuantity(), minimumAskedQuantity)));
			}
		}

		logger.info("Traitement finis avec success");
		logger.info("start updating item");
		cartItems.forEach(
				a -> a.getItem().setQuantity(Math.subtractExact(a.getItem().getQuantity(), a.getAskedQuantity())));
		logger.info("updated success");
		return total;
	}

}
