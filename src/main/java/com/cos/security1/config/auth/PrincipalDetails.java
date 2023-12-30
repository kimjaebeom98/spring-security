package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어줍니다. (Security ContextHolder)에다가 세션정보를 저장
// 무슨 말이냐면 session 공간은 같은데 시큐리티 session이 생김 즉 키값으로 구분할거고 이는 Security ContextHolder임
// 시큐리티 세션에 들어갈 수 있는 오브젝트가 정해져있는데 이는 Authentication타입 객체
// Authentication 안에 User정보가 있어야 됨 근데 이는 정해져있음
// User 오브젝트 타입 => UserDetails 타입 객체

// Security Session => Authentication 객체 => UserDetails 타입 객체
// Session에서 Authentication 객체를 꺼내고 또 그 안에 있는 UserDetails 객체를 꺼내면 유저 정보에 접근가능
// 따라서 UserDetails를 상속받은 PrincipalDetails 객체를 Authentication에 넣어줘야함
// Authentication 넣어주는 곳은 PrincipalDetailsService로 구현
public class PrincipalDetails implements UserDetails{
	
	private User user;
	
	// 생성자 생성
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// 해당 User의 권한을 리턴하는 곳!!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// User의 권한 Stirng 타입의 ROLE_USER, ROLE_ADMIN이라서 변형 시켜줘야함
		// ArrayList는 Collection의 자식임
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
