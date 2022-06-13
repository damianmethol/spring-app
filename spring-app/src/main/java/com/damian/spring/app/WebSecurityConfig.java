package com.damian.spring.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(resources).permitAll()
            .antMatchers("/", "/index", "/signup").permitAll()
            	.anyRequest().authenticated()
            	.and()
            .formLogin()
            	.loginPage("/login")
            	.permitAll()
            	.defaultSuccessUrl("/userForm")
            	.failureUrl("/login?error=true")
            	.usernameParameter("username")
            	.passwordParameter("password")
            	.and()
            	.csrf().disable()
            .logout()
            	.permitAll()
            	.logoutSuccessUrl("/login?logout");
            
            ;
        
        
        return http.build();
    }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	
}
