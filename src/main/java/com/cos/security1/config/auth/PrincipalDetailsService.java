package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

// 메모리에 띄어줌
// PrincipalDetails는 new로 강제로 띄어줄거라서 어노테이션 설정 x
@Service
public class PrincipalDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	// 시큐리티 설정에서 loginProcessingUrl("/login");
	// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
	// loadUserByUsername이 리턴이 되면 Authentication 내부에 UserDetails(PrincipalDetails)가 들어가고
	// 또 Session에 UserDetails를 가진 Authentication이 들어감
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loadUserByUsername 의 파라미터인 username은 loginForm.html의 input name = "username"이랑 매칭
		// 만약 username말고 userId로 바꾸고자 하면 스프링 시큐리티 설정에서 .usernameParamether("userId")로 설정
		
		System.out.println("username : " + username);
		User userEntity = userRepository.findByUsername(username); // Jpa Query method
		System.out.println("role : " + userEntity.getRole());
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		
		return null;
	}
	
}
