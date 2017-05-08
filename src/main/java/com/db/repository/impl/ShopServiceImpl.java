package com.db.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.entity.Shop;
import com.db.repository.ShopRepository;
import com.db.repository.ShopService;
import com.db.utils.UtilsShop;

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
		addShopGeoLocation(shop);
		shopRepository.save(shop);

	}

	private void addShopGeoLocation(Shop shop) {
		UtilsShop.updateGeolocation(shop);
	}

	@Override
	public Optional<Shop> findByName(String name) {
		return shopRepository.findByName(name);
	}

	@Override
	public Shop findOne(long id) {
		return shopRepository.findOne(id);
	}

}
