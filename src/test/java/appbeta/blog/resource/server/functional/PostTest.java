package appbeta.blog.resource.server.functional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts="classpath:sql/test_data.sql")
public class PostTest extends AbstractFunctional {
	
	protected String url = "/post";
	protected int userPostId = 10;
	protected int adminPostId = 1;
			
	private ObjectNode getJsonResponsePost10() {
		return getJsonObj()
				.put("id", 10)
				.put("date", "2020-05-11 11:26:06")
				.put("title", "tytul 10")
				.put("content",  "tresc 10")
				.put("userId", 2)
				.put("userName", "test_user");
	}
	
	@Test
	public void getOwnPostAsUser() throws Exception {
		mockMvc.perform(
				get(url + "/" + userPostId)
				.with(getUser()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(getJsonResponsePost10().toString(), false));
	}
	
	@Test
	public void getSomeonesElsePostAsUser() throws Exception {
		mockMvc.perform(
				get(url + "/" + adminPostId)
				.with(getUser()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(adminPostId));
	}
	
	@Test
	public void getPostAsNotAuthenticated() throws Exception {
		mockMvc.perform(
				get(url + "/" + userPostId))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(userPostId));
	}
	
	@Test
	public void getPostsSortedByDateAscending() throws Exception {		
		mockMvc.perform(
				get(url)
				.param("sort", "date,asc")
				.param("size", "3")
				.param("page", "1")
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.size").value(3))
			.andExpect(jsonPath("$.totalElements").value(16))
			.andExpect(jsonPath("$.number").value(1))
			.andExpect(jsonPath("$.content[0].id").value(4))
			.andExpect(jsonPath("$.content[1].id").value(5))
			.andExpect(jsonPath("$.content[2].id").value(6));
	}

	@Test
	public void addPostAsUser() throws Exception {
		String json = getJsonObj()
				.put("title", "test post title 1")
				.put("content", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
				.toString();
		
		mockMvc.perform(
				post(url)
				.with(getUser())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(json, false))
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.userName").value(userName))
			.andExpect(jsonPath("$.date").isString());
	}
	
	@Test 
    public void failToAddPostAsUnauthorized() throws Exception {
		String json = getJsonObj()
				.put("title", "test post title 1")
				.put("content", "Lorem ipsum dolor sit amet")
				.toString();
		
    	mockMvc.perform(
    			post("/post")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(json))
    		.andDo(print())
    		.andExpect(status().isForbidden());
	}
	
	@Test
	public void editOwnPostAsUser() throws Exception{
		int postId = userPostId;
		String json = getJsonObj()
				.put("title", "edited tile")
				.put("content", "edited content")
				.toString();
		
		mockMvc.perform(
				put(url + "/" + postId)
				.with(getUser())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(json, false))
			.andExpect(jsonPath("$.id").value(postId));
	}
	
	@Test
	public void editSomeonesElsePostAsAdmin() throws Exception{
		int postId = userPostId;
		String json = getJsonObj()
				.put("title", "edited tile")
				.put("content", "edited content")
				.toString();
		
		mockMvc.perform(
				put(url + "/" + postId)
				.with(getAdmin())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(json, false))
			.andExpect(jsonPath("$.id").value(postId));
	}
	
	@Test
	public void failToEditSomeonesElsePostAsUser() throws Exception{
		int postId = adminPostId;
		String json = getJsonObj()
				.put("title", "edited tile")
				.put("content", "edited content")
				.toString();
		
		mockMvc.perform(
				put(url + "/" + postId)
				.with(getUser())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isForbidden());
	}
	
	@Test
	public void deleteOwnPostAsUser() throws Exception {
		mockMvc.perform(
				delete(url + "/" + userPostId)
				.with(getUser()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void failToDeleteSomeonesElsePostAsUser() throws Exception {
		mockMvc.perform(
				delete(url + "/" + adminPostId)
				.with(getUser()))
			.andExpect(status().isForbidden());
	}
	
	@Test
	public void deleteSomeonesElsePostAsAdmin() throws Exception {
		mockMvc.perform(
				delete(url + "/" + userPostId)
				.with(getUser()))
			.andExpect(status().isOk());
	}
}
