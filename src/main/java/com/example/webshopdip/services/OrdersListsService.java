package com.example.webshopdip.services;

import com.example.webshopdip.dtos.*;
import com.example.webshopdip.entities.*;
import com.example.webshopdip.exceptions.CategoriesGoodsNotFoundException;
import com.example.webshopdip.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrdersListsService {
    @Autowired
    private OrdersListsRepository ordersListsRepository;
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private GoodsOrdersRepository goodsOrdersRepository;
    @Autowired
    private DeliveriesMethodRepository deliveriesMethodRepository;
    @Autowired
    private PaymentsTypeRepository paymentsTypeRepository;

    //    public OrdersListsDTO createOrdersLists(OrdersListsDTO dto) throws OrdersListsAlreadyExistException {
//        if (ordersListsRepository.findByNumber(dto.getNumber()) != null) {//dto.getNumber()) != null
//            throw new OrdersListsAlreadyExistException("Такий номер вже існує");
//        }
//
//        OrdersListsEntity entity = new OrdersListsEntity();
//        entity.setNumber(dto.getNumber());
//        entity.setCustomers(dto.getCustomers(entity.getCustomers()));
//        entity.setPaymentsType(dto.getPaymentsType());
//        entity.setDeliveriesMethod(dto.getDeliveriesMethod());
//        entity.setAddress_delivery(dto.getAddress_delivery(entity.getAddress_delivery()));
//        entity.setGoodsOrders(dto.getGoodsOrders(entity.getGoodsOrders()));
//
//        OrdersListsEntity savedEntity = ordersListsRepository.save(entity);
//
//        return entityToDTO(savedEntity);
//    }
    public OrdersListsDTO addGoodsToOrder(Long customerId) {

        CustomersEntity customers = customersRepository.findById(customerId).orElse(null);
        OrdersListsEntity ordersLists = new OrdersListsEntity();
        ordersLists.setCustomers(customers);
        ordersListsRepository.save(ordersLists);
        return  entityToDTO(ordersLists);
    }

    public ResponseEntity<String> makeOrder(OrdersListToSaveDTO ordersListToSaveDTO) {

        try {
                Optional<OrdersListsEntity> optionalOrdersLists = ordersListsRepository.findById(ordersListToSaveDTO.getId());

                if (optionalOrdersLists.isPresent()) {
                    OrdersListsEntity ordersLists = optionalOrdersLists.get();
                    ordersLists.setAddress_delivery(ordersListToSaveDTO.getAddress_delivery());
                    ordersLists.setDeliveriesMethod(deliveriesMethodRepository.findById(ordersListToSaveDTO.getDeliveriesMethodId()).orElse(null));
                    ordersLists.setPaymentsType(paymentsTypeRepository.findById(ordersListToSaveDTO.getPaymentsTypeId()).orElse(null));
                    ordersLists.setDate_order(LocalDate.now());
                    ordersLists.setNumber(ordersLists.getId().toString()+"-"+ ordersLists.getDate_order().toString());

                    ordersListsRepository.save(ordersLists);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orders not found with id: " + ordersListToSaveDTO.getId());
                }

            return ResponseEntity.ok("Замовлення товару успішно оброблено");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public OrdersListsDTO entityToDTO(OrdersListsEntity entity) {
        OrdersListsDTO dto = new OrdersListsDTO();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setCustomers(entity.getCustomers());
        dto.setPaymentsType(entity.getPaymentsType());
        dto.setAddress_delivery(entity.getAddress_delivery());
        dto.setGoodsOrders(entity.getGoodsOrders());
        return dto;
    }

    public OrdersListsEntity getOne(Long id) throws CategoriesGoodsNotFoundException {
        Optional<OrdersListsEntity> optional = ordersListsRepository.findById(id);
        if (optional.isEmpty()) {
            throw new CategoriesGoodsNotFoundException("Категорію товару не знайдено");
        }
        return optional.get();
    }


}