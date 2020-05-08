package appbeta.blog.resource.server.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/post/**").permitAll()
				.antMatchers("/user").hasAnyRole("ADMIN", "USER")
				.antMatchers("/user/**", "/role/**").hasRole("ADMIN")
				.anyRequest().authenticated().and()
			.oauth2ResourceServer()
				.jwt().jwtAuthenticationConverter(getJwtAuthenticationConverter());
	}
	
	private JwtAuthenticationConverter getJwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtConv = new JwtAuthenticationConverter();
		JwtGrantedAuthoritiesConverter authConv = new JwtGrantedAuthoritiesConverter();
		authConv.setAuthoritiesClaimName("authorities");
		authConv.setAuthorityPrefix("ROLE_");
		jwtConv.setJwtGrantedAuthoritiesConverter(authConv);
		return jwtConv;
	}
	
}
