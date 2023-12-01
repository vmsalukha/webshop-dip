package com.example.webshopdip.services;

import com.example.webshopdip.dtos.CustomersDTO;
import com.example.webshopdip.dtos.CustomersFullDTO;
import com.example.webshopdip.dtos.GoodsOrdersWithGoodDTO;
import com.example.webshopdip.dtos.SellersDTO;
import com.example.webshopdip.entities.CustomersEntity;
import com.example.webshopdip.entities.SellersEntity;
import com.example.webshopdip.exceptions.CustomersNotFoundException;
import com.example.webshopdip.exceptions.SellersNotFoundException;
import com.example.webshopdip.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    GoodsOrdersService goodsOrdersService;
    public CustomersEntity createCustomers(CustomersEntity customersEntity){
        return customersRepository.save(customersEntity);
    }
//    public CustomersEntity getOne(Long id) throws CustomersNotFoundException {
//        Optional<CustomersEntity> optional = customersRepository.findById(id);
//        if(optional.isEmpty()){
//            throw new CustomersNotFoundException("Користувача не знайдено");
//        }
//        return optional.get();
//    }


    public ResponseEntity<?> updateCustomer(Long id, CustomersFullDTO updatedData, HttpServletRequest request) {
        try {
            Optional<CustomersEntity> optional = customersRepository.findById(id);
            if (optional.isEmpty()) {
                throw new CustomersNotFoundException("Користувача не знайдено");
            }

            CustomersEntity customer = optional.get();

            // Оновлення даних з DTO
            customer.setName(updatedData.getName());
            customer.setPhone(updatedData.getPhone());
            customer.setEmail(updatedData.getEmail());
            customer.setAddress(updatedData.getAddress());

            // Збереження оновлених даних в базі даних
            customersRepository.save(customer);

            // Повернення оновлених даних користувачу
            return new ResponseEntity<>(entityToDTOFull(request, customer), HttpStatus.OK);
        } catch (CustomersNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Помилка у виконанні запиту", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getOne(Long id) {
        try {
            Optional<CustomersEntity> optional = customersRepository.findById(id);
            if (optional.isEmpty()) {
                throw new CustomersNotFoundException("Користувача не знайдено");
            }
            CustomersEntity customer = optional.get();
            return new ResponseEntity<>(entityToDTO(customer), HttpStatus.OK);
        } catch (CustomersNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Помилка у виконанні запиту", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAll() {
        try {
            Iterable<CustomersEntity> customersEntities = customersRepository.findAll(Sort.by(Sort.Order.asc("id")));

            Iterable<CustomersDTO> customersDTOs = StreamSupport.stream(customersEntities.spliterator(), false)
                    .map(this::entityToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(customersDTOs, HttpStatus.OK);
        } catch (Exception e) {
            // Обробка помилки
            return new ResponseEntity<>("Помилка у виконанні запиту", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public void deleteCustomers(Long id) {
        customersRepository.deleteById(id);
    }

    public CustomersDTO entityToDTO(CustomersEntity entity) {
        CustomersDTO dto = new CustomersDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
    public ResponseEntity<?> getOneFull(HttpServletRequest request, Long id) {
        try {
            Optional<CustomersEntity> optional = customersRepository.findById(id);
            if (optional.isEmpty()) {
                throw new CustomersNotFoundException("Користувача не знайдено");
            }
            CustomersEntity customer = optional.get();
            return new ResponseEntity<>(entityToDTOFull(request, customer), HttpStatus.OK);
        } catch (CustomersNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Помилка у виконанні запиту", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public CustomersFullDTO entityToDTOFull(HttpServletRequest request, CustomersEntity entity) {
        CustomersFullDTO dto = new CustomersFullDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        List<GoodsOrdersWithGoodDTO> goods = goodsOrdersService.getShopingByCustomerId(entity.getId(), request);
        dto.setGoods(goods);
        return dto;
    }
}