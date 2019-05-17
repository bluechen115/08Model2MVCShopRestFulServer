package com.model2.mvc.web.product;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.board.product.ProductBoardService;
import com.model2.mvc.service.domain.Discount;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.ProductBoard;
import com.model2.mvc.service.domain.ProductInfo;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

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
	
	@RequestMapping(value="json/getProduct/{boardNo}/{menu}")
	public Map<String,Object> getProduct(@PathVariable int boardNo,
											@PathVariable String menu,
											HttpServletRequest request,
											HttpServletResponse response,
											HttpSession session
											) throws Exception {
		System.out.println("json/getProduct");
		
		User user=(User)session.getAttribute("user");
		
		if(menu.equals("search")) {
			productBoardService.addViewCount(boardNo);
		}
		
		Map<String,Object> map = productBoardService.getProductBoardByBoardNo(boardNo);
		ProductBoard productBoard = (ProductBoard)map.get("productBoard");
		Discount discount = (Discount)map.get("discount");
		
		Product product = productService.getProductByBoardNo(boardNo);
		
		/*int purchaseCount = purchaseService.getCountPurchase(user.getUserId());
		int price=product.getPrice();
		if(productBoard.getBoardNo()==discount.getDiscountBoard()) {
			price=(int)(product.getPrice()*0.75);
		}
		if(purchaseCount % 4 == 0) {
			price=(int)(price*0.9);
		}
		
		product.setResultPrice(price);*/
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		resultMap.put("productBoard", productBoard);
		resultMap.put("product", product);
		resultMap.put("discount", discount);
		resultMap.put("user", user);
		//resultMap.put("purchaseCount", purchaseCount);
		
		return resultMap;
	}
	
	@RequestMapping(value="json/listProduct",method=RequestMethod.POST)
	public Map<String,Object> getListProduct(@RequestBody Search search,
												@RequestBody Page page) throws Exception{
		System.out.println("json/listProduct");
		
		page.setPageUnit(pageUnit);
		
		if(page.getPageSize()==0) {
		page.setPageSize(pageSize);
		}
		
		if(search.getSearchKeyword()!=null) {
				search.setSearchKeyword(search.getSearchKeyword());
				System.out.println("POST방식으로 실행, SearchKeyword :: "+search.getSearchKeyword());
		}
		
		search.setPageSize(page.getPageSize());
		
		Map<String,Object> map = productBoardService.getProductBoardList(search);
		
		Page resultPage=new Page(search.getCurrentPage(),((Integer)map.get("totalCount")).intValue(), page.getPageUnit(), page.getPageSize());
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("list", map.get("list"));
		resultMap.put("discount", map.get("discount"));
		resultMap.put("resultPage", resultPage);
		
		return resultMap;
	}
	
	@RequestMapping("json/updateProduct")
	public Map<String, Object> updateProduct(@RequestBody ProductInfo productInfo)
												throws Exception{
		System.out.println("json/updateProduct.do");
		
		ProductBoard productBoard = productInfo.getProductBoard();
		System.out.println("//"+productBoard.getBoardNo());
		Product product = productInfo.getProduct();
		
		productBoardService.modifyProductBoard(productBoard);
		productService.updateProduct(product);
		
		
		product =  productService.getProductByBoardNo(productBoard.getBoardNo());
		
		Map<String,Object> mapProdBoard = productBoardService.getProductBoardByBoardNo(productBoard.getBoardNo());
		productBoard = (ProductBoard)mapProdBoard.get("productBoard");
		Discount discount = (Discount)mapProdBoard.get("discount");
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("productBoard", productBoard);
		map.put("product", product);
		map.put("discount", discount);
		
		return map;
	}
}
