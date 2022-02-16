package br.com.lunchbreak.lunchbreak.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/home").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.antMatchers("/election/**").hasAnyRole("USER")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		ArrayList<UserDetails> users = new ArrayList<UserDetails>();
		users.add( User.withDefaultPasswordEncoder()
		.username("user1")
		.password("password")
		.roles("USER")
		.build());
		users.add( User.withDefaultPasswordEncoder()
		.username("user2")
		.password("password")
		.roles("USER")
		.build());
		users.add( User.withDefaultPasswordEncoder()
		.username("admin")
		.password("password")
		.roles("ADMIN")
		.build());

		return new InMemoryUserDetailsManager(users);
	}
}