package com.example.webshopdip.controllers;

import com.example.webshopdip.dtos.GoodsOrdersDTO;
import com.example.webshopdip.entities.PaymentsTypeEntity;
import com.example.webshopdip.services.PaymentsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/paymentsType")
public class PaymentsTypeController {
    @Autowired
    private PaymentsTypeService paymentsTypeService;

    @GetMapping// 2023/11/12
    public ResponseEntity<List<PaymentsTypeEntity>> getAll(HttpServletRequest request) {
        List<PaymentsTypeEntity> paymentsTypeEntities = paymentsTypeService.getAll(request);
        return new ResponseEntity<>(paymentsTypeEntities, HttpStatus.OK);
    }
}
