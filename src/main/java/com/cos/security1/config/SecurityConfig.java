package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // Secured 어노테이션 활성화, preAuthorisze 어노테이션 활성화 << 왜 쓰냐면 메소드 하나만 권한 체크 걸고 싶을 때 주로 씀
public class SecurityConfig {
	
	// 해당 메서드의 리턴되는 오브젝트를 loC로 등록
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests()
				.antMatchers("/user/**").authenticated() // 인증만 되면 들어갈 수 있는 주소
				.antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.loginPage("/loginForm")
				.loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌  따라서 컨트롤러에 /login을 만들지 않아도 시큐리티가 대신 해줌
				.defaultSuccessUrl("/");  // 무조건 "/"로 가는게 아니고 사용자가 특정 페이지로 갈려고 했다가 로그인 요청을 하면 로그인 후 특정 페이지로 감    
				
				
		return http.build();
				
				
	}
}
