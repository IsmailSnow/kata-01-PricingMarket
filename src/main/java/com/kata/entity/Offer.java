package com.kata.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***
 * This class define the offer of an item an offer is a discount it have an unique id ,description, code , quantityCondition , offerType
 * 
 **/

@Entity
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String description;
	@NotNull
	private String code;
	@NotNull
	private Integer quantityCondition;

	@Enumerated(EnumType.STRING)
	private OfferType offerType;

	@ManyToMany(mappedBy = "offers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Item> items;

	public Offer() {
		super();
	}

	public Offer(String description, String code, Integer quantityCondition, OfferType offerType) {
		super();
		this.description = description;
		this.code = code;
		this.quantityCondition = quantityCondition;
		this.offerType = offerType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OfferType getOfferType() {
		return offerType;
	}

	public void setOfferType(OfferType offerType) {
		this.offerType = offerType;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getQuantityCondition() {
		return quantityCondition;
	}

	public void setQuantityCondition(Integer quantityCondition) {
		this.quantityCondition = quantityCondition;
	}

	public Boolean isoOfferApplicableFOrItem(Integer askedQuantity) {
		return askedQuantity >= quantityCondition && !offerType.equals(OfferType.none);
	}

}
