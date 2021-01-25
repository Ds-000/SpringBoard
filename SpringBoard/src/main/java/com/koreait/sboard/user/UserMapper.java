package com.koreait.sboard.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.UserEntity;

@Mapper
public interface UserMapper {
	
	int insUser(UserEntity param);
	
	UserEntity selUser(UserEntity param); 
}
