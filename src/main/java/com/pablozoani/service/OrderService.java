package com.pablozoani.service;

import com.pablozoani.api.v1.model.ItemDTO;
import com.pablozoani.api.v1.model.ItemDTOv2;
import com.pablozoani.api.v1.model.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> findOrderByCustomerId(Long customerId);

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    void deleteOrderById(Long id);

    OrderDTO cancelOrderById(Long id);

    OrderDTO purchaseOrderById(Long id);

    List<ItemDTO> getItemsByOrderId(Long id);

    ItemDTO createItemByOrderId(Long id, ItemDTOv2 itemDTO);

    void deleteItemByItemIdAndOrderId(Long orderId, Long itemId);

    ItemDTO getItemByOrderIdAndItemId(Long orderId, Long itemId);
}
