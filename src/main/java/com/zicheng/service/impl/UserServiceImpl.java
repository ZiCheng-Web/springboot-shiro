package com.zicheng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zicheng.domain.User;
import com.zicheng.mapper.UserMapper;
import com.zicheng.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	//注入Mapper接口
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findByName(String username) {
		return userMapper.findByName(username);
	}

	@Override
	public User findById(Integer id) {
		return userMapper.findById(id);
	}

	
}
