package com.questionpro.assesment.service;


import com.questionpro.assesment.dto.GroceryItemDto;
import com.questionpro.assesment.dto.GroceryItemListDto;
import com.questionpro.assesment.model.GroceryItem;
import java.util.List;

public interface GroceryItemService {

  GroceryItemListDto getItems();
  GroceryItemListDto createItem(List<GroceryItemDto> items);
  GroceryItemListDto deleteItem(String id);
  GroceryItemListDto updateItem(String id, GroceryItemDto item);
  GroceryItemListDto updateInventory(String id, int quantity);
  List<GroceryItem> getAllItems(List<String> itemIds);
}
