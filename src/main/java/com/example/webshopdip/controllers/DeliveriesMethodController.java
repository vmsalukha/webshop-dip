package com.example.webshopdip.controllers;

import com.example.webshopdip.entities.DeliveriesMethodEntity;
import com.example.webshopdip.entities.PaymentsTypeEntity;
import com.example.webshopdip.services.DeliveriesMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/deliveriesMethod")
public class DeliveriesMethodController {
    @Autowired
    public DeliveriesMethodService deliveriesMethodService;
    @GetMapping// 2023/11/12
    public ResponseEntity<List<DeliveriesMethodEntity>> getAll(HttpServletRequest request) {
        List<DeliveriesMethodEntity> deliveriesMethodEntities = deliveriesMethodService.getAll(request);
        return new ResponseEntity<>(deliveriesMethodEntities, HttpStatus.OK);
    }
}
