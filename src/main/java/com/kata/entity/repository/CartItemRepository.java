package com.kata.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kata.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
