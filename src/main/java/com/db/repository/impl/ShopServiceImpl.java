package com.db.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.entity.Shop;
import com.db.repository.ShopRepository;
import com.db.repository.ShopService;


@Service
@Transactional
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopRepository shopRepository;

	@Override
	public List<Shop> findAll() {
		return shopRepository.findAll();
	}



	@Override
	@Transactional
	public void saveShop(Shop shop) {
		Optional<Shop> testShop = this.findByName(shop.getName());
		System.out.println("valor de la tienda FindBy "+testShop.toString());
		System.out.println("valor de la tienda INSERTED "+shop.toString());
		
		shopRepository.save(shop);
	}

	@Override
	public Optional<Shop> findByName(String name) {
		return shopRepository.findByName(name);
	}



	@Override
	public Shop findOne(long id) {
		return shopRepository.findOne(id);
	}



	@Override
	public Optional<Shop> findByNameAndId(String name, long id) {
		return shopRepository.findByNameAndId(name,id);
	}



	

}
