package com.kata.ressource.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
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

public class TraitementUniqueOffre implements ChainCalculAndUpdate {

	private final static Logger logger = LoggerFactory.getLogger(TraitementUniqueOffre.class);

	private ChainCalculAndUpdate nextInChain;

	@Override
	public void setNextOperation(ChainCalculAndUpdate nextChain) {
		this.nextInChain = nextChain;

	}

	@Override
	public BigDecimal calculateAmout(Map<OfferType, List<CartItem>> map, BigDecimal t) {
		List<CartItem> list = map.get(OfferType.unique);
		BigDecimal total = BigDecimal.ZERO;
		if(CollectionUtils.isEmpty(list)) return total.add(nextInChain.calculateAmout(map,total));
		List<BigDecimal> listofTotals = list.stream().map(a -> calcultAndUpdateItem(a)).collect(Collectors.toList());
		BigDecimal totalCost = listofTotals.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        total = total.add(totalCost);
        logger.info("item with unique offer cost "+totalCost);
        total = total.add(nextInChain.calculateAmout(map, total));
		return total;
	}

	private BigDecimal calcultAndUpdateItem(CartItem cartItem) {
		Item item = cartItem.getItem();
		logger.info("recuepration des codes d'offres applicables pour l'item");
		Set<Offer> offers = item.getOffers().stream()
				.filter(a -> a.isoOfferApplicableFOrItem(cartItem.getAskedQuantity())).collect(Collectors.toSet());
		logger.info("Recuperation success");
		int askedQuantity = cartItem.getAskedQuantity();
		BigDecimal total = BigDecimal.ZERO;
		for (Offer offre : offers) {
			OfferStrategy offerStrategy = StrategyDefiner.defineStrategyForUniqueOffer(offre.getCode(), askedQuantity);
			item.setOfferStrategy(offerStrategy);
			Boolean isAvilableForOffre = askedQuantity >= offre.getQuantityCondition();
			if (askedQuantity > 0 && isAvilableForOffre) {
				BigDecimal totalCost = item.OperationStrategy(cartItem);
				total = total.add(totalCost,new MathContext(5));
				askedQuantity = Math.subtractExact(askedQuantity, offre.getQuantityCondition());
				isAvilableForOffre = askedQuantity >= offre.getQuantityCondition();
				
			}
			if (askedQuantity > 0 && !isAvilableForOffre) {
				total = total.add(item.calculTotalWithoutOffer(askedQuantity),new MathContext(5));
				askedQuantity = 0;
			}
		}
		item.setQuantity(Math.subtractExact(item.getQuantity(), cartItem.getAskedQuantity()));
		logger.info("le montant total de l'item "+item.getName()+" is" +total);
		return total;
	}

}
