package com.kata.ressource.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.kata.entity.CartItem;
import com.kata.entity.Item;
import com.kata.entity.OfferType;
import com.kata.ressource.service.ChainCalculAndUpdate;

/**
 * Traitement des elements de la map qui ont OfferType is none
 * ***/

public class TraitementWithoutOffre implements ChainCalculAndUpdate {
	private final static Logger logger = LoggerFactory.getLogger(TraitementWithoutOffre.class);

	private ChainCalculAndUpdate nextInChain;

	@Override
	public void setNextOperation(ChainCalculAndUpdate nextInChain) {
		this.nextInChain = nextInChain;
	}

	@Override
	public BigDecimal calculateAmout(Map<OfferType, List<CartItem>> map, BigDecimal totalCost) {
		List<CartItem> list = map.get(OfferType.none);
		if (CollectionUtils.isEmpty(list))
			return BigDecimal.valueOf(0).add(nextInChain.calculateAmout(map, BigDecimal.valueOf(0)));
		BigDecimal total = list.stream().map(a -> calculAndUpdateInformation(a)).collect(Collectors.toList()).stream()
				.reduce(BigDecimal.ZERO, BigDecimal::add).add(totalCost);
		logger.info("item wihtout offer cost " + total);
		BigDecimal nextTotalForUniqueOffer = nextInChain.calculateAmout(map, totalCost);// callind to next traitement 
		total = total.add(nextTotalForUniqueOffer);// adding the result of the next traitement to the actual one
		return total;
	}

	private BigDecimal calculAndUpdateInformation(CartItem cartItem) {
		logger.info("traitement d'item " + cartItem.getItem().getName());
		Item item = cartItem.getItem();// here we can add a test to check if item is not null , but i already add a validation NotNull in the entity cartItem 
		                               // so the item can never null 
		item.setQuantity(Math.subtractExact(item.getQuantity(), cartItem.getAskedQuantity()));// update item information
		logger.info("item updated " + cartItem.getItem().getName());
		BigDecimal totalCost = item.calculTotalWithoutOffer(cartItem.getAskedQuantity());
		logger.info("l'item " + item.getName() + " cost " + totalCost);
		return totalCost;
	}

}
