package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncode;
	
	@GetMapping({"", "/"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	// 회원가입 페이지
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	// 회원가입 처리
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		// 비밀번호 암호화 !!
		// 단순 문자열로 저장하면 시큐리티가 작동 못함 이유는 패스워드가 암호화가 안돼서
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncode.encode(rawPassword);
		user.setPassword(encPassword);
		
		userRepository.save(user); 
		
		return "redirect:/loginForm";
	}
	
	@GetMapping("/info")
	@Secured("ROLE_ADMIN")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@GetMapping("/data")
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 메서드가 시작되기 전에 체크, 메서드 종료된 이후에 체크면 PostAuthorize << 쓰는 일 별로 없음
	public @ResponseBody String data() {
		return "데이터정보";
	}

}
