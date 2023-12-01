package com.example.webshopdip.controllers;

import com.example.webshopdip.dtos.CustomersFullDTO;
import com.example.webshopdip.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CustomersController {
    @Autowired
    private CustomersService customersService;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomersFullDTO updatedData, HttpServletRequest request)
    {
        return customersService.updateCustomer(id, updatedData, request);
    }

    @GetMapping("/getOne")
    public ResponseEntity<?> getOneGood(@RequestParam Long id, HttpServletRequest request) {
        return customersService.getOne(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return customersService.getAll();
    }

    @GetMapping("/getOneFull")
    public ResponseEntity<?> getOneFull(HttpServletRequest request, @RequestParam Long id) {
        return customersService.getOneFull(request, id);
    }
}
