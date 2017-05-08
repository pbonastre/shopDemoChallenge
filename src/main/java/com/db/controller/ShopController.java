package com.db.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.db.entity.Shop;
import com.db.exception.ShopNotFoundException;
import com.db.repository.ShopService;
import com.db.resource.ShopResource;


@RestController
@RequestMapping("/shops")
public class ShopController {

	private static final int ONE = 1;


	private static final String INITIAL_URI = "http://localhost:8080/shops/";


	@Autowired
	private ShopService shopService;


	private static final Logger LOGGER = LoggerFactory.getLogger(ShopController.class);


	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
	"application/hal+json" })
	public ResponseEntity<List<Shop>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(shopService.findAll());
	}


	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addShop(@RequestBody final Shop shop) {
		LOGGER.info("Adding shop: " + shop);
	validateShop(shop);
	populateUri(shop);
	
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(new ShopResource(shop).getLink("self").getHref()));
   
		if (isPresent(shop)) {
			shopService.saveShop(shop);
		 	return new ResponseEntity<>(shop, headers, HttpStatus.OK);
		} else {
 			shopService.saveShop(shop);
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
			
		}
	}




	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ExceptionHandler(ShopNotFoundException.class)
	public ResponseEntity<Shop> findShopByName(@PathVariable String name) {
		Shop shop = this.shopService.findByName(name).orElseThrow(() -> new ShopNotFoundException(name));
		return ResponseEntity.status(HttpStatus.OK).body(shop);
	}

	private boolean isPresent(Shop shop) {
		return shopService.findByName(shop.getName()).isPresent();
	}
	
	private void validateShop(Shop shop) {
		Assert.notNull(shop, "Shop object expected");
		Assert.notNull(shop.getName(), "Shop Name expected");
		Assert.notNull(shop.getShopAddress(), "Shop ShopAddress expected");
		Assert.notNull(shop.getShopAddress().getNumber(), "Shop Number expected");
		Assert.notNull(shop.getShopAddress().getPostCode(), "ShoPostCode expected");
	}
	
	private void populateUri(Shop shop) {
		shop.setUri(INITIAL_URI+shop.getName());
	}

}
