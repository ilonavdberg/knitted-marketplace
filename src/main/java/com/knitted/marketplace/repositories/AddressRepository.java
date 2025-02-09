package com.knitted.marketplace.repositories;

import com.knitted.marketplace.models.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
