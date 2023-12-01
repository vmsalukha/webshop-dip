package com.example.webshopdip.services;

import com.example.webshopdip.dtos.GoodsOrdersDTO;
import com.example.webshopdip.entities.GoodsOrdersEntity;
import com.example.webshopdip.entities.PaymentsTypeEntity;
import com.example.webshopdip.repositories.PaymentsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class PaymentsTypeService {
    @Autowired
    private PaymentsTypeRepository paymentsTypeRepository;

    public List<PaymentsTypeEntity> getAll(HttpServletRequest request) {
        List<PaymentsTypeEntity> paymentsTypeEntities = paymentsTypeRepository.findAll();
//        Iterable<PaymentsTypeEntity> paymentsTypeEntities = paymentsTypeRepository.findAll();
        return paymentsTypeEntities;
    }
}
