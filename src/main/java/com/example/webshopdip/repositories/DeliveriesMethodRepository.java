package com.example.webshopdip.repositories;

import com.example.webshopdip.entities.DeliveriesMethodEntity;
import com.example.webshopdip.entities.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveriesMethodRepository extends JpaRepository<DeliveriesMethodEntity, Long> {
}
