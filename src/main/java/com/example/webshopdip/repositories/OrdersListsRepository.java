package com.example.webshopdip.repositories;

import com.example.webshopdip.entities.OrdersListsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersListsRepository extends JpaRepository<OrdersListsEntity,Long> {
    OrdersListsEntity findByNumber (String number);//по номеру замовлення
    OrdersListsEntity findByNumberIsNullAndCustomers_Id  (Long customerId);//по id замовлення
    // Метод для пошуку OrdersListsEntity за customers.id і порожнім number
    List<OrdersListsEntity> findByCustomers_IdAndNumberIsNull(Long customerId);

    // Метод для пошуку OrdersListsEntity за customers.id і не порожнім number
    List<OrdersListsEntity> findByCustomers_IdAndNumberIsNotNull(Long customerId);
    List<OrdersListsEntity> findByCustomers_Id(Long id);


}