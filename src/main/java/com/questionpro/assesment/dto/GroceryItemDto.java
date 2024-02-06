package com.questionpro.assesment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroceryItemDto {

  private String id;
  private String name;
  private Double price;
  private Integer quantity;
}
