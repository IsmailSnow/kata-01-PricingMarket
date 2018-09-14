package com.kata.entity;

import java.math.BigDecimal;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 
 * This class define a shoppingCart
 * Every shoppingCart has :a list of cartItem 
 *                         an id 
 *                         Total Amout with discount
 * */
@Entity
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal totalAmoutWithDiscount;

	@OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<CartItem> items;

	public ShoppingCart() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalAmoutWithDiscount() {
		return totalAmoutWithDiscount;
	}

	public void setTotalAmoutWithDiscount(BigDecimal totalAmoutWithDiscount) {
		this.totalAmoutWithDiscount = totalAmoutWithDiscount;
	}

	public Set<CartItem> getItems() {
		return items;
	}

	public void setItems(Set<CartItem> items) {
		this.items = items;
	}

}
