package utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.db.entity.Shop;
import com.db.entity.ShopAddress;
import com.db.repository.ShopService;
import com.db.utils.UtilsShop;


public class UtilShopTest {

		private static final Logger log = LoggerFactory.getLogger(UtilShopTest.class);
	static final String SHOP_ID = "shop1";
	static final String SHOP_ID2 = "shop2";
	static final String SHOP_ID3 = "shop3";
	static final String SHOP_NUMBER = "1";
	static final String SHOP_NUMBER2 = "2";
	static final String SHOP_NUMBER89 = "89";
	static final String SHOP_POST_CODE = "46022";
	static final String SHOP_POST_CODE2 = "46023";
	static final String SHOP_POST_CODE3 = "46021";
	static final Double INITIAL_LAT = 44.5810869;
	static final Double INITIAL_LNG =1.946368;
	static final Double FINAL_LAT =39.4471372;
	static final Double FINAL_LNG =-0.3348372;
	static final Double OTHER_LAT =37.149022;
	static final Double OTHER_LNG =-88.943163;
	static final Double OTHER_LAT2 =36.9245629;
	static final Double OTHER_LNG2 =-88.6144839;
	static final String ADRESS ="Barlow, Kentucky 42024, EE. UU.";
	
	private List<Shop> shopList = new ArrayList<>();
	
	@Test
	public void updateLongAndLatTest() {
		ShopAddress shopAddress = new ShopAddress(SHOP_NUMBER89, SHOP_POST_CODE3);
		Shop newshop = new Shop(SHOP_ID, shopAddress);
		UtilsShop.updateGeolocation(newshop);
		assertEquals(newshop.getShopAddress().getLatitude(), INITIAL_LAT);
		assertEquals(newshop.getShopAddress().getLongitude(), INITIAL_LNG);

	}
	
	@Test
	public void findTheNearestShopTest() {
		
		ShopAddress initialshopAddress = new ShopAddress(SHOP_NUMBER, SHOP_POST_CODE);
		Shop initialShop = new Shop(SHOP_ID, initialshopAddress);
		initialShop = UtilsShop.addPositionInfo(initialShop);
		shopList.add(initialShop);
		
		ShopAddress shopAddress2 = new ShopAddress(SHOP_NUMBER2, SHOP_POST_CODE2);
		Shop shop2 = new Shop(SHOP_ID2, shopAddress2);
		shop2 = UtilsShop.addPositionInfo(shop2);
		shopList.add(shop2);
		
		
		ShopAddress shopAddress3 = new ShopAddress(SHOP_NUMBER89, SHOP_POST_CODE3);
		Shop shop3 = new Shop(SHOP_ID3, shopAddress3);
		shop3 = UtilsShop.addPositionInfo(shop3);
		shopList.add(shop3);
		
		Shop nearestShop = UtilsShop.nearestShop(INITIAL_LAT,INITIAL_LNG,shopList);
	
		assertEquals(nearestShop.getName(), SHOP_ID3);
		
	}


}
