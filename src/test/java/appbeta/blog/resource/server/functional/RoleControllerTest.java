package appbeta.blog.resource.server.functional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts="classpath:sql/roleController_functional.sql")
@WithMockUser(roles = {"ADMIN"})
public class RoleControllerTest extends AbstractFunctionalTest {
		
	protected String url = "/role";
	
	private ObjectNode getRoleAdminJson() {
		return getJsonObj()
				.put("id", 1)
				.put("name", "ROLE_ADMIN");
	}

	@Test
	public void getAllRoles() throws Exception {
		ObjectNode listObj = getJsonObj();
		listObj.putArray("content")
			.add(getJsonObj()
					.put("id", 4)
					.put("name", "ROLE_TEST_2")
			);
		String list = listObj.toString();
		
		mockMvc.perform(
				get(url)
				.param("size", "3")
				.param("page", "1")
				.param("sort", "id,asc")
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.size").value(3))
			.andExpect(jsonPath("$.totalElements").value(4))
			.andExpect(jsonPath("$.numberOfElements").value(1))
			.andExpect(jsonPath("$.number").value(1))
			.andExpect(content().json(list, false));
	}
	
	@Test
	public void getSingleRole() throws Exception{
		ObjectNode jsonRole = getRoleAdminJson();
		
		mockMvc.perform(
				get(url + "/" + jsonRole.get("id")))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(jsonRole.toString(), false));
	}
	
	@Test
	public void addRole() throws Exception {
		String json = getJsonObj()
				.put("name", "roleController_test_role")
				.toString();
		
		mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(json, false))
			.andExpect(jsonPath("$.id").isNumber());
	}
	
	@Test
	public void editRole() throws Exception{
		String json = getJsonObj()
				.put("name", "EDITED_role")
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
	public void deleteRole() throws Exception{
		mockMvc.perform(
				delete(url + "/1"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
