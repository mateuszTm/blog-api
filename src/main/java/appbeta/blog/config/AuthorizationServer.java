package appbeta.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		// client-secret -> $2a$10$GEqNDMckLYgJxZfRRmP/n.aZUF0u3a.gHPTgCOT.bWTqLRj/PR2qy
		configurer.inMemory()
				.withClient("client-id")
				.secret("$2a$10$GEqNDMckLYgJxZfRRmP/n.aZUF0u3a.gHPTgCOT.bWTqLRj/PR2qy")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.scopes("read", "write", "trust")
				.accessTokenValiditySeconds(1*60*60)
				.refreshTokenValiditySeconds(3*60*60)
				.resourceIds("blog-api");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(authenticationManager);
	}
}
