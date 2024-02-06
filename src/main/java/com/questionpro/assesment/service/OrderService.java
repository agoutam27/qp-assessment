package com.questionpro.assesment.service;

import com.questionpro.assesment.dto.GroceryItemListDto;
import com.questionpro.assesment.dto.OrderDto;

public interface OrderService {

  GroceryItemListDto createOrder(OrderDto orderDto);
}
