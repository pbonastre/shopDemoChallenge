package  com.db.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShopNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	

	public ShopNotFoundException(String shopName) {
		 
		super("could not find shop inserted '" + shopName + "'.");
	}
}

