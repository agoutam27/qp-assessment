package com.questionpro.assesment.service.impl;

import static com.questionpro.assesment.util.Util.isBlank;

import com.questionpro.assesment.constant.Status;
import com.questionpro.assesment.dto.GroceryItemDto;
import com.questionpro.assesment.dto.GroceryItemListDto;
import com.questionpro.assesment.exception.GenericException;
import com.questionpro.assesment.model.GroceryItem;
import com.questionpro.assesment.repository.GroceryItemRepository;
import com.questionpro.assesment.service.GroceryItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GroceryItemServiceImpl implements GroceryItemService {

  @Autowired
  private GroceryItemRepository repository;

  @Override
  public GroceryItemListDto getItems() {
    List<GroceryItem> items = repository.findAll();
    List<GroceryItemDto> itemDtos = items
      .stream()
      .map(model -> GroceryItemDto
        .builder()
        .id(model.getId())
        .name(model.getName())
        .price(model.getPrice())
        .quantity(model.getQuantity())
        .build()
      )
      .collect(Collectors.toList());

    return GroceryItemListDto.builder().status(Status.SUCCESS).data(itemDtos).build();
  }

  @Override
  public GroceryItemListDto createItem(List<GroceryItemDto> items) {
    List<GroceryItem> models = new ArrayList<>(items.size());
    for (GroceryItemDto dto : items) {
      if (isBlank(dto.getId()) || isBlank(dto.getName()) || dto.getPrice() < 0 || dto.getQuantity() < 0) {
        throw new GenericException("invalid payload", HttpStatus.BAD_REQUEST);
      }
      GroceryItem item = new GroceryItem();
      item.setId(dto.getId());
      item.setName(dto.getName());
      item.setPrice(dto.getPrice());
      item.setQuantity(dto.getQuantity());
      models.add(item);
    }
    try {
      repository.saveAllAndFlush(models);
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return GroceryItemListDto.builder().status(Status.SUCCESS).data(items).message("items created successfully").build();
  }

  @Override
  public GroceryItemListDto deleteItem(String id) {
    Optional<GroceryItem> item;
    try {
      item = repository.findById(id);
      if (item.isEmpty()) {
        throw new GenericException("id doesn't exist", HttpStatus.BAD_REQUEST);
      }
      repository.removeById(id);
    } catch (GenericException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return GroceryItemListDto
      .builder().status(Status.SUCCESS).message("item with id " + id + " deleted successfully").build();
  }

  @Override
  public GroceryItemListDto updateItem(String id, GroceryItemDto item) {

    if (isBlank(item.getName()) && item.getQuantity() == null && item.getPrice() == null) {
      throw new GenericException("invalid payload", HttpStatus.BAD_REQUEST);
    }

    Optional<GroceryItem> oldItem;
    GroceryItemDto newItem;
    try {
      oldItem = repository.findById(id);
      if (oldItem.isEmpty()) {
        throw new GenericException("id doesn't exist", HttpStatus.BAD_REQUEST);
      }
      String newName = isBlank(item.getName()) ? oldItem.get().getName() : item.getName();
      Double newPrice = item.getPrice() == null ? oldItem.get().getPrice() : item.getPrice();
      Integer newQty = item.getQuantity() == null ? oldItem.get().getQuantity() : item.getQuantity();

      if (newPrice < 0 || newQty < 0) {
        throw new GenericException("invalid payload", HttpStatus.BAD_REQUEST);
      }
      repository.updateNameAndPriceAndQuantityById(newName, newPrice, newQty, id);
      newItem = new GroceryItemDto(id, newName, newPrice, newQty);
    } catch (GenericException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return GroceryItemListDto
      .builder().status(Status.SUCCESS).message("Updated successfully").data(List.of(newItem)).build();
  }

  @Override
  public GroceryItemListDto updateInventory(String id, int quantity) {
    if (quantity < 0) {
      throw new GenericException("invalid quantity", HttpStatus.BAD_REQUEST);
    }
    Optional<GroceryItem> item;

    try {
      item = repository.findById(id);
      if (item.isEmpty()) {
        throw new GenericException("invalid id", HttpStatus.BAD_REQUEST);
      }
      repository.updateQuantityById(quantity, id);
    } catch (GenericException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return GroceryItemListDto.builder().status(Status.SUCCESS).message("updated inventory successfully").build();
  }

  @Override
  public List<GroceryItem> getAllItems(List<String> itemIds) {
    return repository.findByIdIn(itemIds);
  }
}
