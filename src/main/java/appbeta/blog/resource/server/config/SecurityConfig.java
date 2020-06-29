package appbeta.blog.resource.server.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Value("${blog.allowed-origins}")
	private String[] allowedOrigins;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
				.antMatchers("/error").permitAll()
				.antMatchers(HttpMethod.GET, "/post/**").permitAll()
				.antMatchers(HttpMethod.POST, "/profile").permitAll()
				.antMatchers("/profile/{\\d+}").hasRole(Role.ADMIN.toString())
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
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
