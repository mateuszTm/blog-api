package appbeta.blog.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.EntityNotFoundException;

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
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import appbeta.blog.entity.Post;
import appbeta.blog.entity.Role;
import appbeta.blog.entity.User;
import appbeta.blog.error.ErrorResponse;

import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import org.hamcrest.Matchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	@Sql(scripts="/sql/test_db_schema.sql")
	public void getAllUsers() throws JSONException, JsonProcessingException {
		User u1 = new User();
		u1.setId(1L);
		u1.setLogin("test_admin");
		u1.setPassword("test_admin");
		u1.setPosts(new ArrayList<Post>());
		
		User u2 = new User();
		u2.setId(2L);
		u2.setLogin("test_user");
		u2.setPassword("test_user");
		u2.setPosts(new ArrayList<Post>());
		
		Role role1 = new Role("ROLE_ADMIN");
		role1.setId(1L);
		Role role2 = new Role("ROLE_USER");
		role2.setId(2L);
		
		Set<Role> roles1 = new HashSet<Role>();
		roles1.add(role1);
		u1.setRoles(roles1);
		
		Set<Role> roles2 = new HashSet<Role>();
		roles2.add(role2);
		u2.setRoles(roles2);
//		
		String expectedJson = objectMapper.writeValueAsString(new User[] {u1, u2});
		
//		String expectedJson = "[{\"id\":1,\"login\":\"test_admin\",\"password\":\"test_admin\",\"posts\":[],\"roles\":[{\"id\":1,\"name\":\"ROLE_ADMIN\"}]},{\"id\":2,\"login\":\"test_user\",\"password\":\"test_user\",\"posts\":[],\"roles\":[{\"id\":2,\"name\":\"ROLE_USER\"}]}]";
		
		ResponseEntity response = restTemplate.getForEntity("/user/", String.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
		
		JSONAssert.assertEquals(expectedJson, response.getBody().toString(), JSONCompareMode.LENIENT);
	}
	
	@Test
	public void FailToGetUnexistingUser() throws JSONException, JsonProcessingException {
		//given
		Long userId = 99999999L;
		ObjectNode expectedJson = objectMapper.createObjectNode();
		expectedJson.put("status", "NOT_FOUND");
		expectedJson.put("message", "User id " + userId + " has not been found");
		String expected = objectMapper.writeValueAsString(expectedJson);
		// when
		ResponseEntity response = restTemplate.getForEntity("/user/" + userId, String.class);
		
		// then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
		String actual = response.getBody().toString();
		JSONAssert.assertEquals(expected, actual, false);
		
		assertThat(actual).contains("\"timestamp\":");
	}
	
	@Test
	@Sql(scripts="/sql/test_db_schema.sql")
	public void FailToAddUserWithoutRoles() throws JsonMappingException, JsonProcessingException, JSONException {
		User u = new User();
		Date date = new Date();
		u.setLogin("new_test_user" + new Timestamp(date.getTime()));
		u.setPassword("new_test_user");
		u.setPosts(new ArrayList<Post>());
		
		ObjectNode jsonErrorObj = getJsonObject().
				put("status", "BAD_REQUEST").
				put("message", "Invalid field value");
		jsonErrorObj.putArray("errors").add(getJsonObject().
				put("message", "must not be empty").
				put("objectName", "user").
				put("fieldName", "roles").
				put("rejectedValue", "null"));
		String jsonError = jsonErrorObj.toString();
		
		ResponseEntity response = restTemplate.postForEntity("/user", u, String.class);
		String jsonResponse = response.getBody().toString();
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		JSONAssert.assertEquals(jsonError, jsonResponse, false);
		assertThat(jsonResponse).contains("timestamp");
	}
	
	//TODO
	@Test
	@Sql(scripts="/sql/test_db_schema.sql")
	public void AddUserWithRoleUser() throws JSONException {
		// given
		ObjectNode jsonObj = objectMapper.createObjectNode();
		jsonObj.put("login", "AddUserWithRole_login");
		jsonObj.put("password", "AddUserWithRole_password");
		jsonObj.putArray("roles").add("ROLE_USER");
		String json = jsonObj.toString();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		// when
		ResponseEntity <String> response = restTemplate.exchange("/user", HttpMethod.POST, new HttpEntity<>(json, headers), String.class);
		
		System.out.println(response.getBody().toString());
		
		// then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
		String responseJson = response.getBody().toString();
		JSONAssert.assertEquals(json, responseJson, false);
		assertThat(response.getBody().toString()).containsPattern("id\":\\d+");
		
	}
	
	private boolean isNumericString(String string) {
		return Pattern.compile("\\d+").matcher(string).matches();
	}
	
	private ObjectNode getJsonObject() {
		return objectMapper.createObjectNode();
	}
	
	@Test
	@Sql(scripts="/sql/test_db_schema.sql")
	public void updateUser() throws JsonProcessingException, JSONException {
//		User u = new User();
//		u.setLogin("test_user-EDITED");
//		u.setPassword("test_user-EDITED");
//		u.setId(1L);
//		u.setLocked(false);
		
		ObjectNode jsonObj = getJsonObject().
				put("id", 2).
				put("login", "test_user-EDITED").
				put("password", "test_user-EDITED");
		jsonObj.putArray("posts");
		jsonObj.putArray("roles").
				add("ROLE_USER");
		String json = jsonObj.toString();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity httpEntity = new HttpEntity<>(json, headers);
		
		ResponseEntity response = restTemplate.exchange("/user", HttpMethod.PUT, httpEntity, String.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(json, response.getBody().toString(), false);
	}
	
	@Test
	@Sql(scripts="/sql/test_db_schema.sql")
	public void deleteUser() {
		
		ResponseEntity response = restTemplate.exchange("/user/1", HttpMethod.DELETE, new HttpEntity(null, new HttpHeaders()), String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
