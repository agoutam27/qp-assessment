package com.questionpro.assesment.repository;

import com.questionpro.assesment.model.GroceryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<GroceryOrder, Long> {
}