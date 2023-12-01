package com.example.webshopdip.services;

import com.example.webshopdip.entities.DeliveriesMethodEntity;
import com.example.webshopdip.repositories.DeliveriesMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;

@Service
public class DeliveriesMethodService {
    @Autowired
    private DeliveriesMethodRepository deliveriesMethodRepository;

    public List<DeliveriesMethodEntity> getAll(HttpServletRequest request){
        List<DeliveriesMethodEntity> deliveriesMethodEntities = deliveriesMethodRepository.findAll();
        return deliveriesMethodEntities;
    }
}
