package com.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.db.entity.ShopAddress;


public interface ShopAddressRepository  extends JpaRepository<ShopAddress, Long> {

}
