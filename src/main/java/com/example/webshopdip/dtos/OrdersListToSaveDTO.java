package com.example.webshopdip.dtos;

import java.time.LocalDate;

public class OrdersListToSaveDTO {
    private Long id; // Унікальний ідентифікатор категорії товару
    private String number; //Номер Замовлення на Товари
    private LocalDate date_order; // Дата замовлення

    // Зв'язок Many-to-One: Багато Замовлень на Товари може стосуватися одного Продавця
    private Long customersId;

    // Зв'язок Many-to-One: Багато Замовлень на Товари відповідає одному Типу оплати за товар
    private Long paymentsTypeId;

    // Зв'язок Many-to-One: Багато Замовлень на Товари відповідає одному Методу доставлення товару
    private Long deliveriesMethodId;
    private String address_delivery;  // Адреса доставлення товару

    /////////сутності що мають відношення One-to-Many з сутністю OrdersLists

    // Зв'язок One-to-Many: Одне Замовлення на Товари може мати багато Переліків Товарів
//    private List<GoodsOrdersEntity> goodsOrders = new ArrayList<>();

    public OrdersListToSaveDTO() {
    }

    public OrdersListToSaveDTO(
            Long id,
            String number,
            LocalDate date_order,
            Long customersId,
            Long paymentsTypeId,
            Long deliveriesMethodId,
            String address_delivery
    ) {
        this.id = id;
        this.number = number;
        this.date_order = date_order;
        this.customersId = customersId;
        this.paymentsTypeId = paymentsTypeId;
        this.deliveriesMethodId = deliveriesMethodId;
        this.address_delivery = address_delivery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate_order() {
        return date_order;
    }

    public void setDate_order(LocalDate date_order) {
        this.date_order = date_order;
    }

    public Long getCustomersId() {
        return customersId;
    }

    public void setCustomersId(Long customersId) {
        this.customersId = customersId;
    }

    public Long getPaymentsTypeId() {
        return paymentsTypeId;
    }

    public void setPaymentsTypeId(Long paymentsTypeId) {
        this.paymentsTypeId = paymentsTypeId;
    }

    public Long getDeliveriesMethodId() {
        return deliveriesMethodId;
    }

    public void setDeliveriesMethodId(Long deliveriesMethodId) {
        this.deliveriesMethodId = deliveriesMethodId;
    }

    public String getAddress_delivery() {
        return address_delivery;
    }

    public void setAddress_delivery(String address_delivery) {
        this.address_delivery = address_delivery;
    }
}
