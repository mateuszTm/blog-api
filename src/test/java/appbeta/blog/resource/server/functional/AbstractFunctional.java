package appbeta.blog.resource.server.functional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractFunctional {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	protected ObjectNode getJsonObj() {
		return objectMapper.createObjectNode();
	}
	
	protected String adminName = "test_admin";
	protected String userName = "test_user";
	protected int adminId = 1; 
	protected int userId = 2;
	
	protected RequestPostProcessor getUser() {
		return jwt()
    			.jwt(Jwt.withTokenValue("test-token")
					.header("typ", "JWT")
					.subject("test_user")
					.build())
    			.authorities(AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
    
    protected RequestPostProcessor getAdmin() {
    	return jwt()
			.jwt(Jwt.withTokenValue("test-token")
				.header("typ", "JWT")
				.subject("test_admin")
				.build())
			.authorities(AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
    }
}
