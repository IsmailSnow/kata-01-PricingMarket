package com.kata.ressource.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.kata.entity.CartItem;
import com.kata.entity.OfferType;

/*this will define a chain of responsabilty that will apply when we are calculating the total cost of each key in our map**/
/*The map contain 3 type of list : element with unique,none,group offer*/
/*this will help to get a chain of calcul that we can deal with it */
public interface ChainCalculAndUpdate {

	public void setNextOperation(ChainCalculAndUpdate nextChain);

	public BigDecimal calculateAmout(Map<OfferType, List<CartItem>> map, BigDecimal totalCost);

}
