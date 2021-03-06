package com.pablozoani.service;

import com.pablozoani.api.v1.mapper.ItemMapper;
import com.pablozoani.api.v1.mapper.OrderMapper;
import com.pablozoani.api.v1.model.ItemDTO;
import com.pablozoani.api.v1.model.ItemDTOv2;
import com.pablozoani.api.v1.model.OrderDTO;
import com.pablozoani.domain.Item;
import com.pablozoani.domain.Product;
import com.pablozoani.exception.ResourceNotFoundException;
import com.pablozoani.repository.ItemRepository;
import com.pablozoani.repository.OrderRepository;
import com.pablozoani.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.pablozoani.domain.State.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper,
                            OrderRepository orderRepository,
                            ItemRepository itemRepository,
                            ItemMapper itemMapper,
                            ProductRepository productRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDTO> findOrderByCustomerId(Long customerId) {
        log.debug("findByCustomerId({})", customerId);
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        log.debug("getOrderById({})", id);
        return orderRepository.findById(id)
                .map(orderMapper::orderToDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteOrderById(Long id) {
        log.debug("deleteOrderById({})", id);
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Order " + id + " not found");
        }
    }

    @Override
    public OrderDTO cancelOrderById(Long id) {
        log.debug("cancelOrderById({})", id);
        return orderRepository.findById(id)
                .map(order -> {
                    if (order.getState().equals(ORDERED)) {
                        order.setState(CANCELED);
                        order = orderRepository.save(order);
                        return orderMapper.orderToDto(order);
                    }
                    throw new ResponseStatusException(CONFLICT,
                            "Order must be in 'ordered' state to be canceled.");
                }).orElseThrow(() -> new ResourceNotFoundException("Order " + id + " not found."));
    }

    @Override
    public OrderDTO purchaseOrderById(Long id) {
        log.debug("purchaseOrderById({})", id);
        return orderRepository.findById(id)
                .map(order -> {
                    if (order.getState().equals(CREATED)) {
                        order.setState(ORDERED);
                        order = orderRepository.save(order);
                        return orderMapper.orderToDto(order);
                    }
                    throw new ResponseStatusException(CONFLICT,
                            "Order must be in 'created' state to be purchased");
                }).orElseThrow(() -> new ResourceNotFoundException("Order " + id + " not found."));
    }

    @Override
    public List<ItemDTO> getItemsByOrderId(Long id) {
        log.debug("getItemsByOrderId({})", id);
        return orderRepository.findById(id)
                .map(order -> {
                    return order.getItems()
                            .stream()
                            .map(itemMapper::itemToDto)
                            .collect(Collectors.toList());
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Order " + id + " not found."));
    }

    @Override
    public ItemDTO createItemByOrderId(Long id, ItemDTOv2 itemDTO) {
        return orderRepository.findById(id)
                .map(order -> {
                    Product product = productRepository.findById(itemDTO.getProductId())
                            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                                    "Product " + id + " not found"));
                    Item item = new Item();
                    item.setProduct(product);
                    item.setQuantity(itemDTO.getQuantity());
                    item.setOrder(order);
                    item = itemRepository.save(item);
                    return itemMapper.itemToDto(item);
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Order " + id + " not found"));
    }

    @Override
    public void deleteItemByItemIdAndOrderId(Long orderId, Long itemId) {
        log.debug("deleteItemByItemIdAndOrderId({})", itemId);
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId);
        } else {
            throw new ResourceNotFoundException("Item " + itemId + " not found.");
        }
    }

    @Override
    public ItemDTO getItemByOrderIdAndItemId(Long orderId, Long itemId) {
        return orderRepository.findById(orderId)
                .map(order -> order.getItems()
                        .stream()
                        .filter(item -> item.getId().equals(itemId))
                        .map(itemMapper::itemToDto)
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                                "Item " + itemId + " not found")))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Order " + orderId + " not found"));

    }
}
