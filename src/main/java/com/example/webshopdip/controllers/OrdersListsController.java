package com.example.webshopdip.controllers;


import com.example.webshopdip.dtos.OrdersListToSaveDTO;
import com.example.webshopdip.dtos.OrdersListsDTO;
import com.example.webshopdip.entities.OrdersListsEntity;
import com.example.webshopdip.repositories.OrdersListsRepository;
import com.example.webshopdip.services.OrdersListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orderLists")
public class OrdersListsController {
    @Autowired
    private OrdersListsService ordersListsService;
    @Autowired
    private OrdersListsRepository ordersListsRepository;

    //    @PostMapping
//    public ResponseEntity<OrdersListsDTO> createOrdersLists(@RequestBody OrdersListsDTO dto) {
    @PostMapping
    public ResponseEntity<OrdersListsDTO> createOrdersLists(@RequestBody Map<String, Long> request) {
        Long customerId = request.get("customers");
        try {
            OrdersListsDTO createdDTO = ordersListsService.addGoodsToOrder(customerId);
            return ResponseEntity.ok(createdDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new OrdersListsDTO()); // or handle the error
        }
    }

    @PostMapping("/makeOrder")
    public ResponseEntity<String> makeOrder(@RequestBody OrdersListToSaveDTO ordersListToSaveDTO) {
        try {
            ordersListsService.makeOrder(ordersListToSaveDTO);
            return ResponseEntity.ok("Замовлення товару успішно оброблено");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/getOne")
    public ResponseEntity getOneOrdersLists(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(ordersListsService.getOne(id));
        }
//        catch (OrdersListsNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Виникла помилка");
        }
    }



    @GetMapping
    public ResponseEntity<Iterable<OrdersListsEntity>> getAll() {
        Iterable<OrdersListsEntity> ordersListsEntities = ordersListsRepository.findAll(Sort.by(Sort.Order.asc("id")));
        return new ResponseEntity<>(ordersListsEntities, HttpStatus.OK);
    }


}