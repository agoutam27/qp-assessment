package com.questionpro.assesment.controller;

import com.questionpro.assesment.dto.GroceryItemListDto;
import com.questionpro.assesment.dto.OrderDto;
import com.questionpro.assesment.service.GroceryItemService;
import com.questionpro.assesment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UserController {

  @Autowired
  private OrderService service;

  @Autowired
  private GroceryItemService itemService;

  @GetMapping("/items")
  public GroceryItemListDto getAllItems() {
    return itemService.getItems();
  }

  @PostMapping("/order")
  public GroceryItemListDto createOrder(@RequestBody OrderDto order) {
    return service.createOrder(order);
  }
}
