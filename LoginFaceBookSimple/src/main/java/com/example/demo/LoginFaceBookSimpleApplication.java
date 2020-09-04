package com.example.demo;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableOAuth2Sso
public class LoginFaceBookSimpleApplication extends WebSecurityConfigurerAdapter {

//	@GetMapping("/")
//	public String welcome(Principal principal) {
//		Map<String, Object> detail = (Map<String, Object>)
//				((OAuth2Authentication) principal).getUserAuthentication()
//				.getDetails();
//		String userName = (String)detail.get("name");
//		return "hi "+ userName +" welcome to my app";
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.antMatcher("/**")
		.authorizeRequests()
				// in spring-boot security /login auto redirect to login page
				// because we use OAuth2 so /login will redirect to face-book login page
				.antMatchers("/", "/login", "/webjars/**")
				.permitAll().anyRequest()
				.authenticated().and().logout()
				.logoutSuccessUrl("/").permitAll();
	}

	// Pricipal object will get data from get request
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(LoginFaceBookSimpleApplication.class, args);
	}

}
