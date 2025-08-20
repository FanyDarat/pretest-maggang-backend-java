package com.example.eccomerce.repository;

import com.example.eccomerce.model.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long>{
}
