package com.questionpro.assesment.service.impl;

import static com.questionpro.assesment.util.Util.isBlank;

import com.questionpro.assesment.constant.Status;
import com.questionpro.assesment.dto.GroceryItemDto;
import com.questionpro.assesment.dto.GroceryItemListDto;
import com.questionpro.assesment.dto.OrderDto;
import com.questionpro.assesment.exception.GenericException;
import com.questionpro.assesment.model.GroceryItem;
import com.questionpro.assesment.model.GroceryOrder;
import com.questionpro.assesment.model.GroceryOrderItem;
import com.questionpro.assesment.model.Users;
import com.questionpro.assesment.repository.OrderRepository;
import com.questionpro.assesment.repository.UsersRepository;
import com.questionpro.assesment.service.GroceryItemService;
import com.questionpro.assesment.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private GroceryItemService groceryItemService;

  @Override
  public GroceryItemListDto createOrder(OrderDto orderDto) {

    if (orderDto == null
      || isBlank(orderDto.getItemIds())
      || isBlank(orderDto.getQuantity())
      || orderDto.getQuantity().size() != orderDto.getItemIds().size()) {
      throw new GenericException("invalid order payload", HttpStatus.BAD_REQUEST);
    }

    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    try {
      Optional<Users> userModel = usersRepository.findByUsername(user.getUsername());

      List<GroceryItem> itemModels = groceryItemService.getAllItems(orderDto.getItemIds());

      if (itemModels.size() != orderDto.getItemIds().size()) {
        throw new GenericException("invalid items ids", HttpStatus.BAD_REQUEST);
      }

      List<GroceryOrderItem> orderItems = new ArrayList<>(itemModels.size());
      List<GroceryItemDto> itemDtos = new ArrayList<>(itemModels.size());
      double totalPrice = 0;
      String orderId = UUID.randomUUID().toString();

      for (int i = 0; i < itemModels.size(); i++) {

        GroceryItem itemModel = itemModels.get(i);
        int requestedQuantity = orderDto.getQuantity().get(i);
        int availableQuantity = itemModel.getQuantity();

        if (requestedQuantity <= 0) {
          throw new GenericException("Invalid quantity requested", HttpStatus.BAD_REQUEST);
        }

        if (requestedQuantity > availableQuantity) {
          throw new GenericException(itemModel.getName()
            + " is not available in the given quantity", HttpStatus.BAD_REQUEST);
        }
        itemModel.setQuantity(availableQuantity - requestedQuantity);

        GroceryOrderItem orderItem = new GroceryOrderItem();
        orderItem.setId(itemModel.getId(), orderId);
        orderItem.setQuantity(requestedQuantity);
        orderItem.setPrice(itemModel.getPrice());
        orderItems.add(orderItem);

        GroceryItemDto itemDto = GroceryItemDto
          .builder()
          .id(itemModel.getId())
          .name(itemModel.getName())
          .price(itemModel.getPrice())
          .quantity(requestedQuantity)
          .build();
        itemDtos.add(itemDto);
        totalPrice += itemModel.getPrice() * requestedQuantity;
      }

      GroceryOrder order = new GroceryOrder();
      order.setOrderItems(orderItems);
      order.setUser(userModel.get());
      order.setId(orderId);

      orderRepository.saveAndFlush(order);
      return GroceryItemListDto.builder().status(Status.SUCCESS).data(itemDtos).totalPrice(totalPrice).build();
    } catch (GenericException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
