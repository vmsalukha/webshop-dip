package com.example.webshopdip.dtos;

public class GoodsOrdersEditQuantityDTO {
    private Long id; // Унікальний ідентифікатор Переліку товару в замовлені
    // Зв'язок Many-to-One: Багато Переліків товару може стосуватися одного Замовлення покупця
    private Integer quantity;

    public GoodsOrdersEditQuantityDTO() {
    }

    public GoodsOrdersEditQuantityDTO(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
