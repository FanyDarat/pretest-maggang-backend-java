package com.example.eccomerce.controller;

import com.example.eccomerce.model.Cart;
import com.example.eccomerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public Cart getCart() {
        return cartService.getCart();
    }

    @PostMapping("/add")
    public Cart addItem(@RequestBody AddItemRequest request) {
        return cartService.addItem(request.getProductId(), request.getQuantity());
    }

    public static class AddItemRequest {
        private Long productId;
        private int quantity;

        public Long getProductId() { return productId; }

        public int getQuantity() { return quantity; }
    }
}
