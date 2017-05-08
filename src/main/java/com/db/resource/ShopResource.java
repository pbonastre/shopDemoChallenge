package com.db.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.db.controller.ShopController;
import com.db.entity.Shop;

public class ShopResource extends ResourceSupport{
	
	private final Shop shop;
	
	public ShopResource(Shop shop) {
		this.shop = shop;
		this.add(new Link(shop.getUri(), "shop-uri"));
		this.add(linkTo(methodOn(ShopController.class, shop.getName()).findShopByName(shop.getName())).withSelfRel());
	}

	public Shop getShop() {
		return shop;
	}


 
}
