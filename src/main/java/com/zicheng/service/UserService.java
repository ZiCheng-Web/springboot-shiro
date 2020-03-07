package com.zicheng.service;

import com.zicheng.domain.User;

public interface UserService {

	public User findByName(String username);
	
	public User findById(Integer id);
}
