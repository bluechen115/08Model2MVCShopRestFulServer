package com.model2.mvc.web.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;

import jdk.nashorn.api.scripting.JSObject;


//==> 회원관리 RestController
@RestController
@RequestMapping("/user/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/getUser/{userId}", method=RequestMethod.GET )
	public User getUser( @PathVariable String userId ) throws Exception{
		
		System.out.println("/user/json/getUser : GET");
		
		//Business Logic
		return userService.getUser(userId);
	}

	@RequestMapping( value="json/login", method=RequestMethod.POST )
	public User login(	@RequestBody User user,
									HttpSession session ) throws Exception{
	
		System.out.println("/user/json/login : POST");
		//Business Logic
		System.out.println("::"+user);
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return dbUser;
	}
	
	@RequestMapping(value="json/addUser", method=RequestMethod.GET)
	public void addUser() throws Exception{
		
	}
	
	@RequestMapping(value="json/addUser",method=RequestMethod.POST)
	public User addUser(@RequestBody User user) throws Exception {
		System.out.println("/user/addUser : POST");
		
		userService.addUser(user);
		
		return userService.getUser(user.getUserId());
	}
	
	@RequestMapping(value="json/updateUser/{userId}", method=RequestMethod.GET)
	public User updateUser(@PathVariable String userId) throws Exception{
		System.out.println("/user/json/updateUser : GET");
		
		return userService.getUser(userId);
	}
	
	@RequestMapping(value="json/updateUser",method=RequestMethod.POST)
	public User updateUser(@RequestBody User user,
								HttpSession session) throws Exception{
		System.out.println("/user/json/updateUser : POST");
		
		userService.updateUser(user);
		
		String sessionId=((User)session.getAttribute("user")).getUserId();
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		return (User)session.getAttribute("user");
	}
	
	@RequestMapping(value="json/checkDuplication",method=RequestMethod.POST)
	public Map<String,Object> checkDuplication(@RequestBody String userId) throws Exception{
		System.out.println("/user/json/checkDuplication : POST");
		
		boolean result=userService.checkDuplication(userId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", new Boolean(result));
		map.put("userId", userId);

		return map;
	}
	
	@RequestMapping(value="json/listUser")
	public Map<String,Object> listUser(@RequestBody Search search) throws Exception{
		System.out.println("/user/json/listUser : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=userService.getUserList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		Map<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("list", map.get("list"));
		hashMap.put("resultPage", resultPage);
		hashMap.put("search", search);
		
		return hashMap;
	}
	
}