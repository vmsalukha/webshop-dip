package com.example.webshopdip.dtos;

import java.time.LocalDate;

public class GoodsInvoicesOnCounterDTO {
    private Long id; // Унікальний ідентифікатор Переліку товару
    private LocalDate date_invoice; // Дата додавання в магазин
    private GoodsGetAllDTO goods; // Товар
    private Float price; // Ціна товару
    private Integer quantityDisplayed; // Кількість товару в магазині
    private Integer quantityTheShop; // Залишок товару в магазині
    private Integer quantitySold; // Кількість проданого товару
    private Integer evaluation; //Оцінка товару
    private Long sellerId; //Продавець даного товару

    public GoodsInvoicesOnCounterDTO() {
    }

    public GoodsInvoicesOnCounterDTO(
            Long id,
            LocalDate date_invoice,
            GoodsGetAllDTO goods,
            Float price,
            Integer quantityDisplayed,
            Integer quantityTheShop,
            Integer quantitySold,
            Integer evaluation,
            Long sellerId
    ) {
        this.id = id;
        this.date_invoice = date_invoice;
        this.goods = goods;
        this.price = price;
        this.quantityDisplayed = quantityDisplayed;
        this.quantityTheShop = quantityTheShop;
        this.quantitySold = quantitySold;
        this.evaluation = evaluation;
        this.sellerId = sellerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_invoice() {
        return date_invoice;
    }

    public void setDate_invoice(LocalDate date_invoice) {
        this.date_invoice = date_invoice;
    }

    public GoodsGetAllDTO getGoods() {
        return goods;
    }

    public void setGoods(GoodsGetAllDTO goods) {
        this.goods = goods;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantityDisplayed() {
        return quantityDisplayed;
    }

    public void setQuantityDisplayed(Integer quantityDisplayed) {
        this.quantityDisplayed = quantityDisplayed;
    }

    public Integer getQuantityTheShop() {
        return quantityTheShop;
    }

    public void setQuantityTheShop(Integer quantityTheShop) {
        this.quantityTheShop = quantityTheShop;
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
