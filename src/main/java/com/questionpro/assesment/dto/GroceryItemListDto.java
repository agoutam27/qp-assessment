package com.questionpro.assesment.dto;

import com.questionpro.assesment.constant.Status;
import java.util.List;
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
public class GroceryItemListDto {

  private Status status;
  private String message;
  private List<GroceryItemDto> data;
  private Double totalPrice;
}
