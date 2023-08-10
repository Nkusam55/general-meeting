package com.liafi.gcmeeting.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.liafi.gcmeeting.security.LoginSuccessHandler;

/**
 * @author narendra kusam
 */

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Bean
	public static PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests((authorize) ->
		authorize
		.antMatchers("/register/**").permitAll()
		.antMatchers("/index").permitAll()
		.antMatchers("/users").hasRole("ADMIN")
				).formLogin()
						/*form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/gcmeeting/login")
                        .defaultSuccessUrl("/gcmeeting/usersList?username={username}", true)
                        .permitAll())*/
						/*
						 * .usernameParameter("username") .permitAll() .successHandler(new
						 * AuthenticationSuccessHandler() {
						 * 
						 * @Override public void onAuthenticationSuccess(HttpServletRequest request,
						 * HttpServletResponse response, Authentication authentication) throws
						 * IOException, ServletException { // run custom logics upon successful login
						 * 
						 * UserDetails userDetails = (UserDetails) authentication.getPrincipal(); String
						 * username = userDetails.getUsername();
						 * 
						 * System.out.println("The user " + username + " has logged in.");
						 * 
						 * response.sendRedirect(request.getContextPath()+
						 * "/gcmeeting/usersList?username="+username); } })
						 */

						/*
						 * form -> form .loginPage("/login") .loginProcessingUrl("/gcmeeting/login")
						 * .defaultSuccessUrl("/gcmeeting/users?status=paid") .permitAll()
						 */
						.loginPage("/login")
						.loginProcessingUrl("/gcmeeting/login")
						.usernameParameter("username")
						.successHandler(loginSuccessHandler)
						.permitAll().and()
						.logout(
								logout -> logout
								.logoutRequestMatcher(new AntPathRequestMatcher("/gcmeeting/logout"))
								.logoutSuccessUrl("/gcmeeting/login?logout")
								.permitAll()
								);
						return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}

}