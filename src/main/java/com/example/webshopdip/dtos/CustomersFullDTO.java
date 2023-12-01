package com.example.webshopdip.dtos;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class CustomersFullDTO {

    private Long id; // Унікальний ідентифікатор Покупця
    private String name; // Ім'я(повне) зареєстрованого Покупця
    private String address; // Адреса проживання зареєстрованого Покупця
    private String phone; // Телефон зареєстрованого Покупця
    private String email; // Електронна скринька зареєстрованого Покупця

    List<GoodsOrdersWithGoodDTO> goods;

    public List<GoodsOrdersWithGoodDTO> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsOrdersWithGoodDTO> goods) {
        this.goods = goods;
    }

    public CustomersFullDTO() {
    }

    public CustomersFullDTO(Long id, String name, String address, String phone, String email, List<GoodsOrdersWithGoodDTO> goods) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.goods = goods;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}