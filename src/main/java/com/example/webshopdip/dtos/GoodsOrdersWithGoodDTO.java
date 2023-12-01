package com.example.webshopdip.dtos;

public class GoodsOrdersWithGoodDTO {
    private Long id; // Унікальний ідентифікатор Переліку товару в замовлені
    // Зв'язок Many-to-One: Багато Переліків товару може стосуватися одного Замовлення покупця
    private Long ordersListsId;
    // Зв'язок Many-to-One: Багато Переліків товару може належати одному Товар з Магазину
    private GoodsGetAllDTO goodsGetAllDTO;

    private Float price;
    private Integer quantity;
    private Integer quantityInShop;

    public GoodsOrdersWithGoodDTO() {
    }

    public GoodsOrdersWithGoodDTO(
            Long id,
            Long ordersListsId,
            GoodsGetAllDTO goodsGetAllDTO,
            Float price,
            Integer quantity,
            Integer quantityInShop
    ) {
        this.id = id;
        this.ordersListsId = ordersListsId;
        this.goodsGetAllDTO = goodsGetAllDTO;
        this.price = price;
        this.quantity = quantity;
        this.quantityInShop = quantityInShop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrdersListsId() {
        return ordersListsId;
    }

    public void setOrdersListsId(Long ordersListsId) {
        this.ordersListsId = ordersListsId;
    }

    public GoodsGetAllDTO getGoodsGetAllDTO() {
        return goodsGetAllDTO;
    }

    public void setGoodsGetAllDTO(GoodsGetAllDTO goodsGetAllDTO) {
        this.goodsGetAllDTO = goodsGetAllDTO;
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

    public Integer getQuantityInShop() {
        return quantityInShop;
    }

    public void setQuantityInShop(Integer quantityInShop) {
        this.quantityInShop = quantityInShop;
    }
}
