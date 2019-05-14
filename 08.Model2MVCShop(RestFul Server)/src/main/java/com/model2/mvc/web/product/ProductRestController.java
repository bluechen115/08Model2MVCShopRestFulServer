package com.model2.mvc.web.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.board.product.ProductBoardService;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.ProductBoard;
import com.model2.mvc.service.domain.ProductInfo;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productBoardServiceImpl")
	private ProductBoardService productBoardService;
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	

	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value="json/addProduct")
	public Map<String, Object> addProduct(@RequestBody ProductInfo productInfo) throws Exception{
		System.out.println("json/addProduct");
		
		Product product = productInfo.getProduct();
		ProductBoard productBoard = productInfo.getProductBoard();
		
		System.out.println("RestController :: "+product);
		System.out.println("RestController :: "+productBoard);
		
		productBoardService.addProductBoard(productBoard);
		
		String manuDate = product.getManuDate().replaceAll("-", "");
		product.setManuDate(manuDate);
		
		for(int i=0;i<productBoard.getQuantity();i++) {
			productService.addProduct(product);			
		}
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("product", product);
		map.put("productBoard", productBoard);
		
		return map;
	}

}
