package appbeta.blog.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts="classpath:sql/postController_functional.sql")
@WithMockUser(roles = {"ADMIN"})
public class PostControllerTest extends AbstractFunctionalTest {
	
	protected String url = "/post";
		
	private String getPostJsonOne() {
		return getJsonObj()
				.put("id", 1)
				.put("date", "2020-03-04 15:16:17")
				.put("title", "test title 1")
				.put("content",  "test content 1")
				.put("userId", 1)
				.put("userName", "test_admin")
				.toString();
	}

	@Test
	@WithMockUser(username="test_user", password="test_user", roles = {"USER"})
	public void addUsersPost() throws Exception {
		String postContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
		String json = getJsonObj()
				.put("title", "test post title 1")
				.put("content", postContent)
				.put("userId", 2)
				.toString();
		
		mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(json, false))
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.userName").isString())
			.andExpect(jsonPath("$.date").isString());
	}
	
	@Test
	public void updateUserPost() throws Exception{
		String json = getJsonObj()
				.put("title", "updated tile")
				.put("content", "updated content")
				.toString();
		
		mockMvc.perform(
				put(url + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(json, false));
	}
	
	@Test
	public void deleteUsersPost() throws Exception {
		mockMvc.perform(
				delete(url + "/1"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getPostById() throws Exception {		
		String json = getPostJsonOne();
		mockMvc.perform(
				get(url + "/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(json, false));
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
			.andExpect(jsonPath("$.totalElements").value(11))
			.andExpect(jsonPath("$.number").value(1))
			.andExpect(jsonPath("$.content[0].date").value("2020-08-09 12:11:12"))
			.andExpect(jsonPath("$.content[1].date").value("2020-08-09 13:11:12"))
			.andExpect(jsonPath("$.content[2].date").value("2020-08-09 14:11:12"));
	}
}
