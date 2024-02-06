package com.questionpro.assesment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
public class GroceryOrderItem {


  @EmbeddedId
  private GroceryOrderItemId id;

  @Getter
  @Setter
  private double price;

  @Getter
  @Setter
  private double quantity;

  public String getItemId() {
    return id != null ? id.getItemId() : null;
  }

  public String getOrderId() {
    return id != null ? id.getOrderId() : null;
  }

  public void setId(String itemId, String orderId) {
    id = new GroceryOrderItemId();
    id.setItemId(itemId);
    id.setOrderId(orderId);
  }


  @Getter
  @Setter
  @Embeddable
  @EqualsAndHashCode
  public static class GroceryOrderItemId {

    @Column(nullable = false)
    private String itemId;

    @Column(nullable = false)
    private String orderId;
  }
}
