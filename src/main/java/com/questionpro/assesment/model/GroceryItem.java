package com.questionpro.assesment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GroceryItem {

  @Id
  private String id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "price", nullable = false)
  private Double price;
  @Column(name = "quantity", nullable = false)
  private Integer quantity; // Inventory level
}
