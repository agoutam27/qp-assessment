package com.questionpro.assesment.controller;

import com.questionpro.assesment.dto.GroceryItemDto;
import com.questionpro.assesment.dto.GroceryItemListDto;
import com.questionpro.assesment.service.GroceryItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private GroceryItemService service;


  @PostMapping("/items")
  public GroceryItemListDto addItems(@RequestBody List<GroceryItemDto> items) {
    return service.createItem(items);
  }

  @DeleteMapping("/items/{id}")
  public GroceryItemListDto deleteItems(@PathVariable("id") String id) {
    return service.deleteItem(id);
  }

  @PutMapping("/items/{id}")
  public GroceryItemListDto updateItem(@PathVariable("id") String id, @RequestBody GroceryItemDto item) {
    return service.updateItem(id, item);
  }

  @PatchMapping("/items/{id}")
  public GroceryItemListDto updateItemInventory(@PathVariable("id") String id, @RequestParam("qty") int qty) {
    return service.updateInventory(id, qty);
  }
}
