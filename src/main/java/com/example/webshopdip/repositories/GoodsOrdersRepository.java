package com.example.webshopdip.repositories;

import com.example.webshopdip.entities.GoodsOrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsOrdersRepository extends JpaRepository<GoodsOrdersEntity, Long> {
    List<GoodsOrdersEntity> findByOrdersLists_IdAndGoodsInvoices_Id(Long ordersListsId, Long goodsInvoicesId);
    List<GoodsOrdersEntity> findByGoodsInvoices_Id(Long goodsInvoicesId);
    List<GoodsOrdersEntity> findByOrdersLists_Id(Long ordersListsId);
}
