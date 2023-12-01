package com.example.webshopdip.dtos;

public class GoodsInvoicesToSaveDTO {
    private Long id; // Унікальний ідентифікатор Переліку товару
    private Long goodsId; // Товар
    private Long sellersId; // Продавець
    private Float price; // Ціна товару
    private Integer quantity; // Кількість товару

    public GoodsInvoicesToSaveDTO() {
    }

    public GoodsInvoicesToSaveDTO(
            Long id,
            Long goodsId,
            Long sellersId,
            Float price,
            Integer quantity
    ) {
        this.id = id;
        this.goodsId = goodsId;
        this.sellersId = sellersId;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getSellersId() {
        return sellersId;
    }

    public void setSellersId(Long sellersId) {
        this.sellersId = sellersId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
