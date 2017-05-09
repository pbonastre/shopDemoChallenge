package com.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SHOPADDRESS")
public class ShopAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ADDRESS_ID")
	private Long addressId;

	@Column(name = "number")
	private String number;

	@Column(name = "postCode")
	private String postCode;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "latitude")
	private Double latitude;

	public ShopAddress() {
	}

	public ShopAddress(String number, String postCode, Double longitude, Double latitude) {
		super();
		this.number = number;
		this.postCode = postCode;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public ShopAddress(String number, String postCode) {
		super();
		this.number = number;
		this.postCode = postCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "ShopAddress [number=" + number + ", postCode=" + postCode + ", longitude=" + longitude + ", latitude="
				+ latitude + "]";
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	};

}
