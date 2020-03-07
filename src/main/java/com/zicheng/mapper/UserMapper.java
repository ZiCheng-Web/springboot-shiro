package com.zicheng.mapper;

import com.zicheng.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper
public interface UserMapper {

	public User findByName(String username);
	
	public User findById(Integer id);
}
