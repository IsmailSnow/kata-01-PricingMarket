package com.kata.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.kata.entity.CartItem;
import com.kata.entity.Offer;
import com.kata.entity.OfferType;

@Component
public class ListToMapConverter implements Function<List<CartItem>, Map<OfferType, List<CartItem>>> {
	private final static Logger logger = LoggerFactory.getLogger(ListToMapConverter.class);

	@Override
	public Map<OfferType, List<CartItem>> apply(List<CartItem> t) {
		return constructListWithItemWithoutOffre(t);

	}

	private Map<OfferType, List<CartItem>> constructListWithItemWithoutOffre(List<CartItem> cartItem) {
		Map<OfferType, List<CartItem>> map = new HashMap<>();
    	List<CartItem> cartItemWithoutOffre = cartItem.stream().filter(a -> a.hasOffer().equals(false))
				.collect(Collectors.toList());
		logger.info("Recuperation des cartItem sans offres");
		// the hasoffer will not deal with the case the item has a group offer because
		// we muse check the other item if he exists
		// So we add a filter to check if the item with group offer can use their offer
		// If not we add them to the list of cartItem WithoutOffre
		List<CartItem> cartItemWithOffre = cartItem.stream().filter(a -> a.hasOffer().equals(true))
				.collect(Collectors.toList());
		logger.info("Operation started for grouped offres");
		List<Offer> offers = new ArrayList<>();
		for(CartItem cartItemm : cartItemWithOffre) {
			List<Offer> offerss = cartItemm.getItem().getOffers().stream().filter(c -> c.getOfferType().equals(OfferType.group)).collect(Collectors.toList());
			offers.addAll(offerss);
		}
		Set<Offer> offersFiltred = new HashSet<>(offers);
		offers.clear();
		offers.addAll(offersFiltred);
		logger.info("recuperation de tous les group offre disponible");
		List<CartItem> listWithApplicableGroupOffer = new ArrayList<>();
		logger.info("Consctruction de la liste des items with applicable group offer");
		for (Offer offer : offers) {
			List<CartItem> listCartItems = cartItemWithOffre.stream()
					.filter(a -> a.getItem().getOffers().contains(offer)).collect(Collectors.toList());
			if (checkIfOfferCanBeApplied(offer).test(listCartItems)) {
				listWithApplicableGroupOffer.addAll(listCartItems);
			}
		}
		logger.info("Consctruction Reussi");
		cartItemWithOffre.removeIf(a -> listWithApplicableGroupOffer.contains(a));
		// Maintenant la list cartItemWithOffre est reduit en items qui ont des offres
		// unique et des offres group non applicable
		// maintenant on va supprimer tous les items qui ont uniquement des offres group
		// non applicable pour construire list d items offre unique applicable
		// le rest s ajoutera a la list without offre
		List<CartItem> listWithApplicableUniqueOffre = new ArrayList<>();
		for(CartItem cartItemm : cartItemWithOffre) {
			List<Offer> offres = cartItemm.getItem().getOffers()
					.stream().filter(b -> b.getQuantityCondition() < cartItemm.getAskedQuantity()
					&& b.getOfferType().equals(OfferType.unique)).collect(Collectors.toList());
			 if(!CollectionUtils.isEmpty(offres)) {
				 listWithApplicableUniqueOffre.add(cartItemm);
			 }
			
		}
		map.put(OfferType.unique, listWithApplicableUniqueOffre);
		logger.info("Ajout a la map les cartItems qui ont des offres unique applicables");
		map.put(OfferType.group, listWithApplicableGroupOffer);
		logger.info("Ajout a la map les cartItems qui ont des offres group applicables");
		cartItemWithOffre.removeIf(a -> listWithApplicableUniqueOffre.contains(a));
		logger.info("recuperation des items with offre non applicable et les ajouter avec listWithoutOffre");
		cartItemWithoutOffre.addAll(cartItemWithOffre);
		map.put(OfferType.none, cartItemWithoutOffre);
		logger.info("list of item with no offer added");

		return map;
	}

	private Predicate<List<CartItem>> checkIfOfferCanBeApplied(Offer offer) {
		return a -> a.stream().filter(b -> b.getItem().getOffers().contains(offer)).collect(Collectors.toList())
				.size() == offer.getQuantityCondition();
	}
	
}
