package appbeta.blog.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
	
	private String url = "/post";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired ObjectMapper objectMapper;
	
	private ObjectNode getJsonObj() {
		return objectMapper.createObjectNode();
	}

	// TODO
	@Test
	public void addUsersPost() throws Exception {
		String postContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
		String json = getJsonObj()
				.put("date", "04-03-2020 12:13:14")
				.put("title", "test post title 1")
				.put("content", postContent)
				.put("user", "")
				.toString();
		
		mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.content().json(json, false))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber());
	}
	
	public void updateUsersPost() {}
	
	public void deleteUsersPost() {}
	
	public void getPostById() {}
	
	// TODO stronnicowanie, sortowanie, jakieś filtry
	public void getLastTenPostsSortedByDate() {}
	
	public void getPostsByUserId() {}
}
