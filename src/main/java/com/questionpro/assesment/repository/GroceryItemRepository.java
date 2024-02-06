package com.questionpro.assesment.repository;

import com.questionpro.assesment.model.GroceryItem;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, String> {
  List<GroceryItem> findByIdIn(Collection<String> ids);
  @Transactional
  @Modifying
  @Query("update GroceryItem g set g.quantity = ?1 where g.id = ?2")
  void updateQuantityById(@NonNull Integer quantity, @NonNull String id);

  @Transactional
  @Modifying
  @Query("update GroceryItem g set g.name = ?1, g.price = ?2, g.quantity = ?3 where g.id = ?4")
  void updateNameAndPriceAndQuantityById(@org.springframework.lang.NonNull String name,
                                         @org.springframework.lang.NonNull double price,
                                         @org.springframework.lang.NonNull int quantity, String id);
  Optional<GroceryItem> findById(@org.springframework.lang.NonNull String id);

  @Transactional
  @Modifying
  long removeById(String id);

  List<GroceryItem> findAll();
}
