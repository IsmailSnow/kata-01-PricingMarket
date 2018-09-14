package com.kata.entity;

import java.math.BigDecimal;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;

/******
 * 
 * an item represent a product
 * it also contain a list of offer
 * an a offerstratgy that will set when we need it , it depend of the code of the offer and also if the offer is applicable in our case
 * 
 * 
 *******/

@Entity
public class Item implements OfferStrategy {

	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(Item.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private Integer quantity;
	@NotNull
	private BigDecimal unitPrice;

	@ManyToMany
	@JsonIgnore
	private Set<Offer> offers;

	@Transient
	protected OfferStrategy offerStrategy;

	public Item() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public Boolean containGroupOffer() {
		return offers.stream().anyMatch(a -> a.getOfferType().equals(OfferType.group));
	}

	public BigDecimal calculTotalWithoutOffer(Integer askedQuantity) {
		BigDecimal itemCost = BigDecimal.ZERO;
		BigDecimal totalCost = BigDecimal.ZERO;
		itemCost = unitPrice.multiply(new BigDecimal(askedQuantity));
		totalCost = totalCost.add(itemCost);
		logger.info("total cost :" + totalCost);
		return totalCost;
	}

	@Override
	public BigDecimal OperationStrategy(CartItem cartItem) {
		return offerStrategy.OperationStrategy(cartItem);
	}

	public void setOfferStrategy(OfferStrategy offerStrategy) {
		this.offerStrategy = offerStrategy;
	}

	@Override
	public BigDecimal OperationStrategy(List<CartItem> cartItems, Integer minimumAskedQuantity) {
		return offerStrategy.OperationStrategy(cartItems, minimumAskedQuantity);
	}

}
