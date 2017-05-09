package com.db.utils;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.db.entity.Shop;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class UtilsShop {

	private static final Logger log = LoggerFactory.getLogger(UtilsShop.class);
	static final String YOUR_API_KEY = "AIzaSyBciQ0h0KlwGtLoG9my9u2xmiaUBiCmg0w";
	
	
	public static void updateGeolocation(final Shop shop) {
		addPositionInfo(shop);

	}
	
	
	public static Shop addPositionInfo(Shop shop) {
		GeoApiContext context = new GeoApiContext().setApiKey(YOUR_API_KEY);
		GeocodingResult[] results;

		try {
			results = GeocodingApi.geocode(context, shop.getShopAddress().getPostCode()).await();
			 log.info("setLatitude " + results[0].geometry.location.lat);
			shop.getShopAddress().setLatitude(results[0].geometry.location.lat);
			shop.getShopAddress().setLongitude(results[0].geometry.location.lng);

		} catch (ApiException e) {
			log.error("ApiException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		} catch (InterruptedException e) {
			log.error("InterruptedException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		} catch (IOException e) {
			log.error("IOException in UtilsShop updateLongAndLat obtaining the coordinate values", e);
		}
		return shop;

	}
	
	public static Shop nearestShop(final double latitude, final double longitude,
			final List<Shop> allShops) {
		return (findTheNearestShop(latitude, longitude, allShops));
	}

	private static Shop findTheNearestShop(double latitude, double longitude,List<Shop> allShops) {
		Shop shopKeyFinal = null;
		double distance;
		Double minimunDistanceBetweenShops = null;
		 ListIterator<Shop> iterator = allShops.listIterator();
		 log.info("Number of shops " + allShops.size());
		while (iterator.hasNext()) {

			 Shop shop = iterator.next();
	
			distance = calculateDistance(latitude, longitude, shop);
			boolean isNotDefinedDistance = minimunDistanceBetweenShops == null;
			if (isNotDefinedDistance) {
				minimunDistanceBetweenShops = distance;
				shopKeyFinal = shop;
			} else if (distance < minimunDistanceBetweenShops) {
				minimunDistanceBetweenShops = distance;
				shopKeyFinal = shop;
			}
		}

		return shopKeyFinal;

	}

	private static double calculateDistance(double latitude, double longitude, Shop shop) {
		return Haversine.distance(latitude, longitude,
				shop.getShopAddress().getLatitude(),
				shop.getShopAddress().getLongitude());
	}
}
