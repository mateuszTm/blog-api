package appbeta.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("blog-api").stateless(false);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
        http.anonymous().disable()
        	.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/user").permitAll()
            .antMatchers(HttpMethod.GET, "/post").permitAll()
    	    .antMatchers("/user/**", "/post", "/post/**").hasAnyRole("ADMIN", "USER")
    	    .antMatchers("/user", "/role", "/role/**").hasRole("ADMIN")
    	    .anyRequest().authenticated()
            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());;
	}
}
