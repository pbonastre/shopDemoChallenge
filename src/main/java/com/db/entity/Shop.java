package com.db.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SHOP")
public class Shop implements Serializable{

	
	
	private static final long serialVersionUID = 1L;


	
	
	@Id
	@Column(name = "name",nullable = false,unique=true, length = 100)
	private String name;
	
	@Column(name = "uri",nullable = true)
	private String uri;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ADDRESS_ID", nullable = false)
	private ShopAddress shopAddress;
	
	
	public Shop(){
	}
	
	public Shop(String name, ShopAddress shopAddress, String uri) {
		super();
		this.name = name;
		this.shopAddress = shopAddress;
		this.uri = uri;
	}
	
	public Shop(String name, ShopAddress shopAddress) {
		super();
		this.name = name;
		this.shopAddress = shopAddress;
	}
	
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ShopAddress getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}

	

	@Override
	public String toString() {
		return "Shop [ name=" + name + ", uri=" + uri + ", shopAddress=" + shopAddress + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
