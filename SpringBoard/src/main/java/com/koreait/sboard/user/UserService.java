package com.koreait.sboard.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.common.Utils;
import com.koreait.sboard.model.UserEntity;

@Service
public class UserService {
	
	@Autowired
	private UserMapper mapper;
	
	public int insUser(UserEntity param) {
		String salt = Utils.gensalt();
		String encrytpw = Utils.hashPassword(param.getUser_pw(), salt);
		
		param.setSalt(salt);
		param.setUser_pw(encrytpw);
		
		return mapper.insUser(param);
	}
	
	public UserEntity selUser(UserEntity param) {
		return mapper.selUser(param);
	}
	
	public int login(UserEntity param, HttpSession hs) {
		UserEntity dbData = selUser(param);// 아이디 있는지 없는지 판단
		if(dbData == null) {
			return 2;
		}
		String cryptLoginPw = Utils.hashPassword(param.getUser_pw(), dbData.getSalt());  //db 비밀번호
		if (!cryptLoginPw.equals(dbData.getUser_pw())) {
			return 3;
		}
		dbData.setSalt(null);
		dbData.setUser_pw(null);
		hs.setAttribute(Const.LOGINUSER, dbData);
		return 1;
		
	}
}
