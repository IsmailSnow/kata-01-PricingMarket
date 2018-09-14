package com.kata.utils;

import com.kata.entity.OfferStrategy;
import com.kata.ressource.service.offers.BuyBothAndGet20PerCentDiscount;
import com.kata.ressource.service.offers.BuyFiveItemAndGetOneForFree;
import com.kata.ressource.service.offers.BuyTwentyAndGet20PerCentDiscount;
import com.kata.ressource.service.offers.BuyTwoDifferentTogetherFor6Euro;
import com.kata.ressource.service.offers.ThreeItemFor20PerCentDicount;
import com.kata.ressource.service.offers.ThreeItemForFiveEuro;
import com.kata.ressource.service.offers.ThreeItemForTwoEuro;

/*
 * 
 * This class define a Strategy Definer 
 * it permit with a given (Code , Asked) to chose the strategy (Discount) that will instantiate for Operation Strategy
 * The first offer finded will applied , there is no order 
 * One offer is applied by item
 * */
public class StrategyDefiner {

	public static OfferStrategy defineStrategyForUniqueOffer(String code, Integer askedQuantity) {
		if (code.equals("104") && askedQuantity >= 20) {
			return new BuyTwentyAndGet20PerCentDiscount();
		}
		if (code.equals("101") && askedQuantity >= 5) {
			return new BuyFiveItemAndGetOneForFree();
		}
		if (code.equals("105") && askedQuantity >= 3) {
			return new ThreeItemForTwoEuro();
		}
		if (code.equals("103") && askedQuantity >= 3) {
			return new ThreeItemForFiveEuro();
		}
		if (code.equals("106") && askedQuantity >= 3) {
			return new ThreeItemFor20PerCentDicount();
		}
		return null;
	}

	public static OfferStrategy defineStrategyForMultipleOffer(String code, Integer askedQuantity) {
		if (code.equals("107") && askedQuantity >= 1) {
			return new BuyBothAndGet20PerCentDiscount();
		}
		if (code.equals("108") && askedQuantity >= 1) {
			return new BuyTwoDifferentTogetherFor6Euro();
		}
		return null;
	}

}
