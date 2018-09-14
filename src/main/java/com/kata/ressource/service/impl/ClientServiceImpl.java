package com.kata.ressource.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.entity.CartItem;
import com.kata.entity.OfferType;
import com.kata.ressource.service.ChainCalculAndUpdate;
import com.kata.ressource.service.ClientService;
import com.kata.utils.ListToMapConverter;

@Service
public class ClientServiceImpl implements ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

	@Autowired
	private ListToMapConverter listToMapConverter;

	@Override
	@Transactional
	public BigDecimal getCommandAmout(List<CartItem> cartiems) {
		logger.info("Verification of avaibility");
		if (checkIfAnyItemIsNotAvailable().test(cartiems)) throw new IllegalArgumentException("one of the item is not available");
		logger.info("recuperation de la list des cart items et enchainement des operations");
		Map<OfferType, List<CartItem>> map = listToMapConverter.apply(cartiems);
		BigDecimal totalCost = BigDecimal.ZERO;
		ChainCalculAndUpdate chain = new TraitementWithoutOffre();
		ChainCalculAndUpdate chain1 = new TraitementUniqueOffre();
		ChainCalculAndUpdate chain2 = new TraitementMultipleOffre();
		chain.setNextOperation(chain1);
		chain1.setNextOperation(chain2);
		BigDecimal result = chain.calculateAmout(map, totalCost);
		logger.info("Operation succed , the total amout after discount is" + result);
		return result;
	}

	private Predicate<List<CartItem>> checkIfAnyItemIsNotAvailable() {
		logger.info("Checking if one of items is not available");
		return p -> p.stream().anyMatch(a -> a.getAskedQuantity() > a.getItem().getQuantity());
	}

}
