package com.kata.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*******
 * 
 * Cart Item will represent the item that we will buy
 **********/

@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private Integer askedQuantity;

	@OneToOne
	private Item item;

	@ManyToOne
	@JoinColumn(name = "shopping_Cart_id")
	@JsonIgnore
	private ShoppingCart shoppingCart;

	public CartItem() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAskedQuantity() {
		return askedQuantity;
	}

	public void setAskedQuantity(Integer askedQuantity) {
		this.askedQuantity = askedQuantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Boolean hasOffreApplicable() {
		return item.getOffers().stream().anyMatch(a -> askedQuantity > a.getQuantityCondition());
	}

	public Boolean hasOffer() {
		// on filtre d abbord les typeOffer puis les offres unique non applicable puis
		// on regarde ce qui reste , les offres groups vont etre traité par dans la
		// suite du programme qui appelle la fonction hasOffer
		List<Offer> offres = item.getOffers().stream().filter(a -> !a.getOfferType().equals(OfferType.none))
				.filter(a -> !(askedQuantity < a.getQuantityCondition() && a.getOfferType().equals(OfferType.unique)))
				.collect(Collectors.toList());
		Boolean offerCanBeApplied = CollectionUtils.isEmpty(offres);
		return !offerCanBeApplied;
	}

}
