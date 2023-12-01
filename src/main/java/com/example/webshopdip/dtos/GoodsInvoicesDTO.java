package com.example.webshopdip.dtos;

import java.time.LocalDate;

public class GoodsInvoicesDTO {
    private Long id; // Унікальний ідентифікатор Переліку товару
    private LocalDate date_invoice; // Дата додавання в магазин
    private GoodsGetAllDTO goods; // Товар
    private Float price; // Ціна товару
    private Integer quantity; // Кількість товару
    private Integer evaluation; //Оцінка товару
    private SellersDTO seller; //Продавець даного товару

    public GoodsInvoicesDTO() {
    }

    public GoodsInvoicesDTO(
            Long id,
            LocalDate date_invoice,
            GoodsGetAllDTO goods,
            Float price,
            Integer quantity,
            Integer evaluation,
            SellersDTO seller
    ) {
        this.id = id;
        this.date_invoice = date_invoice;
        this.goods = goods;
        this.price = price;
        this.quantity = quantity;
        this.evaluation = evaluation;
        this.seller = seller;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public SellersDTO getSeller() {
        return seller;
    }

    public void setSeller(SellersDTO seller) {
        this.seller = seller;
    }
}