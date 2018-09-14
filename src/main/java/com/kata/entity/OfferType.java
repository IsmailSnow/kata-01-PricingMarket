package com.kata.entity;

/******
 * 
 * 
 * Offer Type : group---> offer that applied only if a group of item exit unique
 * --> offer that applied only if the quantity condition < the asked quantity
 * none : for item with no offer (in the map of the converter none will
 * represent also element with not applicable offer)
 * 
 * 
 * 
 *******/
public enum OfferType {
	none, unique, group

}
