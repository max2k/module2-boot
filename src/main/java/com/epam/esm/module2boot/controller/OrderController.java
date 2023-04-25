package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.dto.OrderDTO;
import com.epam.esm.module2boot.dto.UserOrderListEntityDTO;
import com.epam.esm.module2boot.model.Order;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;


    @PostMapping("/new")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO));
    }

    @GetMapping("/listByUserID/{id}")
    public ResponseEntity<PagedModel<UserOrderListEntityDTO>> getOrdersByUserId(@PathVariable Integer id,
                                                                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<UserOrderListEntityDTO> userOrderListEntityDTOPage = orderService.getOrderListByUserId(id, page, size);

        userOrderListEntityDTOPage.forEach(o -> o.add(linkTo(methodOn(OrderController.class)
                .getOrder(o.getId())).withRel("GetOrderDetails")));

        PagedModel<UserOrderListEntityDTO> userOrderListEntityDTOPagedModel =
                PagedModel.of(userOrderListEntityDTOPage.toList(),
                        new PagedModel.PageMetadata(size.longValue(),
                                page.longValue(),
                                userOrderListEntityDTOPage.getTotalElements()));

        userOrderListEntityDTOPagedModel.add(linkTo(methodOn(OrderController.class)
                .getOrdersByUserId(id, page, size)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(userOrderListEntityDTOPagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(id));
    }

    @GetMapping("/mostWidelyUsedTag")
    public ResponseEntity<Tag> getMostWidelyUsedTag() {
        Tag tag = orderService.getMostWidelyUsedTag();
        tag.add(linkTo(methodOn(TagController.class)
                .getTagById(tag.getId())).withRel("GetTagDetails"));
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getMostWidelyUsedTag());
    }

}
