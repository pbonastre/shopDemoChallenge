package com.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.db.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop,Long> {
	@Query(value = "select * from #{#entityName} b where b.name=?1", nativeQuery = true)
	 Optional<Shop> findByName(String name);
	
}