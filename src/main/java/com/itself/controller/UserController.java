package com.itself.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itself.domain.po.UserPO;
import com.itself.service.UserService;


@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/userList",method=RequestMethod.GET)
	public Object getUserList(HttpServletRequest request,HttpServletResponse response){
		logger.info("查询用户列表");
		try {
			List<UserPO> users = userService.getUser();
			for(UserPO p : users){
				System.out.println(p.getName());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	@RequestMapping(value="/checkMobile",method=RequestMethod.GET)
	public Object checkMobile(HttpServletRequest request,HttpServletResponse response){
		logger.info("校验手机号");
		try {
			List<UserPO> users = userService.getUser();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
}
