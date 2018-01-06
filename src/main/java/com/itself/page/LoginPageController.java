package com.itself.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itself.domain.po.UserPO;
import com.itself.service.UserService;


/**
* @ClassName: LoginPageController 
* @Description: 页面跳转
* @author it2cgy@163.com
* @date 2017年12月6日 下午11:48:17 
*
 */
@Controller
@RequestMapping("/loginPage")
public class LoginPageController {
	
	static final Logger logger = LoggerFactory.getLogger(LoginPageController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login")
	public String login(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.info("跳转到登陆页面");
		List<UserPO> users = userService.getUser();
		model.addAttribute("users",users);
		return "/login/login";
	}

}
