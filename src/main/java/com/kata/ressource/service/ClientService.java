package com.kata.ressource.service;

import java.math.BigDecimal;
import java.util.List;

import com.kata.entity.CartItem;

public interface ClientService {

	BigDecimal getCommandAmout(List<CartItem> cartiems);

}
