package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.dto.UserDTO;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.service.OrderService;
import com.epam.esm.module2boot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// to add all api endpoints
// https://betterprogramming.pub/building-an-api-to-list-all-endpoints-exposed-by-spring-boot-645f1f64ebf3

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    // add new user entry
    @PostMapping("/new")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUserDTO = userService.createUser(userDTO);

            createdUserDTO.add(linkTo(methodOn(UserController.class)
                    .getUser(createdUserDTO.getId())).withSelfRel());

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
        } catch (Exception e) {
            throw new BadRequestException("User with this name cannot be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        try {
            UserDTO userDTO = userService.getUserDTO(id);
            userDTO.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
            userDTO.add(linkTo(methodOn(OrderController.class)
                    .getOrdersByUserId(id, 0, 10)).withRel("orders"));
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } catch (Exception e) {
            throw new BadRequestException("User with this id cannot be found");
        }
    }

}
