package com.example.eccomerce.repository;

import com.example.eccomerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
}
