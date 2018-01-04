package com.itself.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itself.common.base.service.BaseService;
import com.itself.common.base.dao.BaseDao;
import com.itself.dao.UserDao;
import com.itself.domain.po.UserPO;


@Service("userService")
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
//	@Autowired
//	private UserDao userDaoImpl;
//	
//	public List<UserPO> getUserList(){
//		List<UserPO> result = userDaoImpl.findAll();
//		return result;
//	}
	 
}
