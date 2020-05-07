package appbeta.blog.resource.server.functional;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts="classpath:sql/userController_functional.sql")
@WithMockUser(roles = {"ADMIN"})
public class UserControllerTest extends AbstractFunctionalTest{
	
	protected String url = "/user";
	
	protected ObjectNode getRoleUserJson() {
		return objectMapper.createObjectNode()
				.put("id", 2)
				.put("name", "ROLE_USER");
	}
	
	protected ObjectNode getRoleAdminJson() {
		return objectMapper.createObjectNode()
				.put("id", 1)
				.put("name", "ROLE_ADMIN");
	}
	
	protected ObjectNode getUserAdminReturnedJson() {
		ObjectNode json = getJsonObj()
				.put("id", 1)
				.put("login", "test_admin");
		json.putArray("roles")
			.add(getRoleAdminJson());
		return json;
	}
		
	protected ObjectNode getUserUserReturnedJson() {
		ObjectNode json = getJsonObj()
				.put("id", 2)
				.put("login", "test_user");
		json.putArray("roles")
			.add(getRoleUserJson());
		return json;
	}
	
	@Test
	public void getAllUsers() throws Exception {
		ObjectNode content = getJsonObj();
		content.putArray("content")
			.add(getUserAdminReturnedJson())
			.add(getUserUserReturnedJson());
		String jsonContent = content.toString();
		
		mockMvc.perform(
				get(url)
				.param("sort", "id,asc"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.totalElements").value(2))
			.andExpect(jsonPath("$.numberOfElements").value(2))
			.andExpect(jsonPath("$.number").value(0))
			.andExpect(content().json(jsonContent, false));
	}
	
	@Test
	public void FailToGetUnexistingUser() throws Exception {
		Long fakeId = 99999999L;
		String errorJson = getJsonObj()
				.put("status", "NOT_FOUND")
				.put("message", "User id " + fakeId + " has not been found")
				.toString();
		
		mockMvc.perform(
				get(url + "/" + fakeId))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(errorJson, false))
			.andExpect(jsonPath("$.time").isString());
	}
	
	@Test
	public void FailToAddUserWithoutRoles() throws Exception {
		String jsonUser = getJsonObj()
				.put("login", "new_test_user")
				.put("password", "new_test_user")
				.toString();
		
		ObjectNode error = getJsonObj()
				.put("status", "BAD_REQUEST")
				.put("message", "Invalid field value");
		error.putArray("errors").add(getJsonObj()
				.put("message", "must not be empty")
				.put("objectName", "user")
				.put("fieldName", "roles")
				.put("rejectedValue", "null"));
		String jsonError = error.toString();
		
		mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonUser))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().json(jsonError, false))
			.andExpect(jsonPath("$.time").isString());
	}
	
	@Test
	public void AddUserWithRoleUser() throws Exception {
		ObjectNode user = getJsonObj()
				.put("login", "AddUserWithRole_login");
		user.putArray("roles")
				.add(getRoleUserJson());
		String jsonUserOut = user.toString();
		
		user.put("password", "AddUserWithRole_password");
		String jsonUserInput = user.toString();
		
		mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonUserInput))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(jsonUserOut, false))
			.andExpect(jsonPath("$.id").isNumber());
	}
	
	@Test
	public void updateUser() throws Exception {		
		ObjectNode user = getUserUserReturnedJson()
				.put("login", "test_user-EDITED");
		int id = user.get("id").asInt();
		String returnedJson = user.toString();
		
		user.put("password", "test_user-EDITED");
		String consumedJson = user.toString();
		
		mockMvc.perform(
				put(url + "/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(consumedJson))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(returnedJson, false));
	}
	
	@Test
	public void deleteUser() throws Exception{
		mockMvc.perform(
				delete(url + "/1"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
