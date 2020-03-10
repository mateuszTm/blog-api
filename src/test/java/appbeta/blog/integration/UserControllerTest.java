package appbeta.blog.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import appbeta.blog.entity.Role;
import appbeta.blog.entity.User;

import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.Matchers;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts="classpath:sql/userController_functional.sql")
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;
	
	private String url = "/user";
	
	private ObjectNode getJsonObj() {
		return objectMapper.createObjectNode();
	}
	
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
	
	protected ObjectNode getAdminJson() {
		ObjectNode json = getJsonObj()
				.put("id", 1)
				.put("login", "test_admin")
				.put("password", "test_admin");
		json.putArray("roles")
			.add(getRoleAdminJson());
		return json;
	}
	
	protected ObjectNode getUserJson() {
		ObjectNode json = getJsonObj()
				.put("id", 2)
				.put("login", "test_user")
				.put("password", "test_user");
		json.putArray("roles")
			.add(getRoleUserJson());
		return json;
	}
	
	@Test
	public void getAllUsers() throws Exception {
		ObjectNode jsonAdmin = getAdminJson();
		ObjectNode jsonUser = getUserJson();
		//		
		ObjectNode content = getJsonObj();
		content.putArray("content")
			.add(getAdminJson())
			.add(getUserJson());
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
		//given
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
			.andExpect(jsonPath("$.timestamp").isString());
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
			.andExpect(jsonPath("$.timestamp").isString());
	}
	
	@Test
	public void AddUserWithRoleUser() throws Exception {		
		ObjectNode user = getJsonObj()
				.put("login", "AddUserWithRole_login")
				.put("password", "AddUserWithRole_password");
		user.putArray("roles").add(getRoleUserJson());
		String jsonUser = user.toString();
		
		mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonUser))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(jsonUser, false))
			.andExpect(jsonPath("$.id").isNumber());
	}
	
	@Test
	public void updateUser() throws Exception {		
		ObjectNode user = getUserJson()
				.put("login", "test_user-EDITED")
				.put("password", "test_user-EDITED");
		int id = user.get("id").asInt();
		String jsonUser = user.toString();
		
		mockMvc.perform(
				put(url + "/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonUser))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(jsonUser, false));
	}
	
	@Test
	public void deleteUser() throws Exception{
		mockMvc.perform(
				delete(url + "/1"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
