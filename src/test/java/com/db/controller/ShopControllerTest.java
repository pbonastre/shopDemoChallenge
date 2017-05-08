package com.db.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.db.ShopDemoChallengeApplication;
import com.db.entity.Shop;
import com.db.entity.ShopAddress;
import com.db.repository.ShopAddressRepository;
import com.db.repository.ShopRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopDemoChallengeApplication.class)
@Transactional
@WebAppConfiguration
public class ShopControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(ShopControllerTest.class);


	private static final String SHOP_TWO = "shopTwo";

	private static final String SHOP_ONE = "shopOne";
	
	private static final String SHOP_NOT_EXISTS = "shopNotExists";

	private static final String SHOP2_URL = "http://localhost:8080/shopTwo";

	private static final String SHOP1_URL = "http://localhost:8080/shopOne";


	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private List<Shop> shopList = new ArrayList<>();

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private ShopAddressRepository shopAddressRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	ShopAddress shopAddressOne = new ShopAddress();

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		this.shopRepository.deleteAllInBatch();
		this.shopAddressRepository.deleteAllInBatch();
		informShopAdressOne(shopAddressOne);
		this.shopList.add(shopRepository.save(new Shop(SHOP_ONE, shopAddressOne, SHOP1_URL)));
		log.debug("Initializing test with shop added");
	}
	
	@After
    public void tearDown() throws Exception {
        shopRepository.deleteAllInBatch();
        log.debug("Finalizing ShopControllerTest");
    }


	private void informShopAdressOne(ShopAddress shopAddress) {
		shopAddress.setLatitude(Double.valueOf(39.4654808));
		shopAddress.setLongitude(Double.valueOf(-0.3605178));
		shopAddress.setNumber("23");
		shopAddress.setPostCode("46021");
	}

	private void informShopAdressTwo(ShopAddress shopAddress) {
		shopAddress.setLatitude(Double.valueOf(39.4876926));
		shopAddress.setLongitude(Double.valueOf(-0.3316349));
		shopAddress.setNumber("24");
		shopAddress.setPostCode("46022");
	}

	@Test
	public void findAll() throws Exception {
	    String jsonShop = json(new Shop(
                SHOP_ONE, shopAddressOne,SHOP1_URL ));

		this.mockMvc.perform(get("/shops/").content(jsonShop).contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(content().contentType(contentType)).andExpect(status().isOk());

	}
	
	@Test
	public void addShop() throws Exception{
		ShopAddress shopAddressTwo = new ShopAddress();
		informShopAdressTwo(shopAddressTwo);
		
	      String shopJson = json(new Shop(
	                SHOP_TWO, shopAddressTwo,SHOP2_URL ));

		// First time is 201 (insert).
		this.mockMvc.perform(post("/shops/").content(shopJson).contentType(contentType))
				.andDo(print()).andExpect(status().isCreated());
		
		// First time is 200 (update).
		this.mockMvc.perform(post("/shops/").content(shopJson).contentType(contentType))
				.andDo(print()).andExpect(status().isOk());
	
	}
	
	@Test
	public void modifyAndExistingShop() throws Exception{
		Shop shopUpdate = shopList.get(0);
		
		ShopAddress shopAddressUpdate = shopUpdate.getShopAddress();
		shopAddressUpdate.setNumber("44");
		shopAddressUpdate.setPostCode("46030");
		shopUpdate.setShopAddress(shopAddressUpdate);
		
	    String jsonMessage = json(shopUpdate);

		this.mockMvc.perform(post("/shops/").contentType(contentType).content(jsonMessage))
				.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
    public void saveInvalidShop() throws Exception   {
				this.mockMvc
				.perform(post("/shops/")
				        .contentType(contentType)
				        .content("{}"))
				.andExpect(status().isBadRequest());
        
    }
	
	@Test
    public void getExistingShop() throws Exception {
		
	    String jsonShopOne = json(new Shop(
                SHOP_ONE, shopAddressOne,SHOP1_URL ));

        this.mockMvc
                .perform(get("/shops/" + shopList.get(0).getName()).contentType(contentType).content(jsonShopOne))
                .andExpect(status().isOk());
    }
	
	
	@Test
    public void NotExistingShop() throws Exception {
		
	    String jsonShopNotExists = json(new Shop(
	    		SHOP_NOT_EXISTS, shopAddressOne,SHOP1_URL ));

        this.mockMvc
                .perform(get("/shops/" + SHOP_NOT_EXISTS).contentType(contentType).content(jsonShopNotExists))
                .andExpect(status().isNotFound());
    }
	
	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
	
	

}
