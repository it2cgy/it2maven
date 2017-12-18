package com.itself.dao.impl;

import org.springframework.stereotype.Repository;

import com.itself.common.base.dao.impl.BaseDaoImpl;
import com.itself.dao.UserDao;
import com.itself.domain.po.UserPO;

@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<UserPO> implements UserDao {

}
