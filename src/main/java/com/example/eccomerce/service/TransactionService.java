package com.example.eccomerce.service;

import com.example.eccomerce.model.*;
import com.example.eccomerce.repository.TransactionRepository;
import com.example.eccomerce.repository.ProductRepository;
import com.example.eccomerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    // Ambil semua transaksi
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    // Ambil transaksi berdasarkan ID
    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    // Update status transaksi
    public Transaction updateStatus(Long id, String status) {
        Transaction transaction = getById(id);
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    // Checkout: buat transaksi dari cart default (id=1)
    @Transactional
    public Transaction createTransaction() {
        Cart cart = cartRepository.findById(1L).orElseGet(() -> {
            Cart newCart = new Cart();
            return cartRepository.save(newCart);
        });

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Transaction transaction = new Transaction();
        transaction.setItems(cart.getItems().stream().map(cartItem -> {
            TransactionItem tItem = new TransactionItem();
            tItem.setProduct(cartItem.getProduct());
            tItem.setQuantity(cartItem.getQuantity());
            tItem.setPrice(cartItem.getProduct().getPrice());
            tItem.setTransaction(transaction);

            // Kurangi stock produk
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            return tItem;
        }).toList());

        transaction.calculateTotal();
        transaction.setStatus("pending");
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Kosongkan cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return savedTransaction;
    }
}
