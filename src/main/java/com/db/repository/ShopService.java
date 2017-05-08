package com.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

import com.db.entity.Shop;



public interface ShopService {
	public List<Shop> findAll();
	public void saveShop(Shop shop);
	
	@Cacheable ("shops")
	public Shop findOne(long id);
	Optional<Shop> findByName(String name);
	Optional<Shop> findByNameAndId(String name, long id);
}
