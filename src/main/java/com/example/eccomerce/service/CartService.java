package com.example.eccomerce.service;

import com.example.eccomerce.model.Cart;
import com.example.eccomerce.model.CartItem;
import com.example.eccomerce.model.Product;
import com.example.eccomerce.repository.CartRepository;
import com.example.eccomerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCart() {
        return cartRepository.findById(1L)
                .orElseGet(() -> cartRepository.save(new Cart()));
    }

    public Cart addItem(Long productId, int quantity) {
        Cart cart = getCart();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        cart.calculateTotal();
        return cartRepository.save(cart);
    }
}
