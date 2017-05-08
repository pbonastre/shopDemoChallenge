package com.db.utils;

public enum ShopStateEnum
{
	UPDATE("Shop has been updated"), 
	NEW("New shop created");
	
	private String shopStatus;
	
	ShopStateEnum(String status){
		this.shopStatus = status;
	}
	
	public String getshopStatus() {
		return shopStatus;
	}


}
