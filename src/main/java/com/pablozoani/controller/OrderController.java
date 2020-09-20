package com.pablozoani.controller;

import com.pablozoani.api.v1.model.ItemDTO;
import com.pablozoani.api.v1.model.OrderDTO;
import com.pablozoani.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(OrderController.BASE_URL)
public class OrderController {

    public static final String BASE_URL = "/api/v1/orders";

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseStatus(OK)
    @GetMapping(produces = {"application/json", "application/hal+json"})
    public CollectionModel<OrderDTO> getAllOrders() {
        return CollectionModel.of(orderService.getAllCustomers()
                .stream()
                .map(orderDTO ->
                        orderDTO.add(linkTo(methodOn(OrderController.class)
                                .getOrderById(orderDTO.getId()))
                                .withSelfRel()))
                .collect(Collectors.toList()));
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{id}",
            produces = {"application/json", "application/hal+json"})
    public EntityModel<OrderDTO> getOrderById(@PathVariable Long id) {
        return EntityModel.of(orderService.getOrderById(id));
    }

    @ResponseStatus(OK)
    @DeleteMapping(value = "/{id}")
    public void deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
    }

    @ResponseStatus(OK)
    @PostMapping(value = "/{id}/actions/cancel",
            produces = {"application/json", "application/hal+json"})
    public EntityModel<OrderDTO> cancelOrderById(@PathVariable Long id) {
        return EntityModel.of(orderService.cancelOrderById(id));
    }

    @ResponseStatus(OK)
    @PostMapping(value = "/{id}/actions/purchase",
            produces = {"application/json", "application/hal+json"})
    public EntityModel<OrderDTO> purchaseOrderById(@PathVariable Long id) {
        return EntityModel.of(orderService.purchaseOrderById(id));
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{id}/items",
            produces = {"application/json", "application/hal+json"})
    public CollectionModel<ItemDTO> getItemsByOrderId(@PathVariable Long id) {
        return CollectionModel.of(orderService.getItemsByOrderId(id));
    }

    @ResponseStatus(CREATED)
    @PostMapping(value = "/{id}/items",
            consumes = {"application/json"})
    public EntityModel<ItemDTO> createItemByOrderId(@PathVariable Long id,
                                                    @RequestBody ItemDTO itemDTO) {
        return EntityModel.of(orderService.createItemByOrderId(id, itemDTO));
    }

    @ResponseStatus(OK)
    @DeleteMapping(value = "/{orderId}/items/{itemId}")
    public void deleteItemByItemIdAndOrderId(@PathVariable Long orderId,
                                             @PathVariable Long itemId) {
        orderService.deleteItemByItemIdAndOrderId(orderId, itemId);
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/{orderId}/items/{itemId}")
    public EntityModel<ItemDTO> getItemByOrderIdAndItemId(@PathVariable Long orderId,
                                                          @PathVariable Long itemId) {
        return EntityModel.of(orderService.getItemByOrderIdAndItemId(orderId, itemId));
    }
}
