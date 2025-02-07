package com.knitted.marketplace.repositories;

import com.knitted.marketplace.models.Shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
