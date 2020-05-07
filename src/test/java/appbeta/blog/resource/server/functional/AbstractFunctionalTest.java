package appbeta.blog.resource.server.functional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractFunctionalTest {

	protected final String roleAdmin = "ADMIN";
	
	protected final String roleUser = "USER";
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	protected ObjectNode getJsonObj() {
		return objectMapper.createObjectNode();
	}
}
