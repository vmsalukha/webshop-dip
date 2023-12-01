package com.example.webshopdip.dtos;

public class CustomersDTO {
    private Long id; // Унікальний ідентифікатор Покупця Товарів
    private String name; // Ім'я(повне) Покупця Товарів

    public CustomersDTO() {
    }

    public CustomersDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
