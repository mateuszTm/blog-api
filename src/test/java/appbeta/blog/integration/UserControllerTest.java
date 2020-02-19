package appbeta.blog.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import appbeta.blog.entity.Post;
import appbeta.blog.entity.Role;
import appbeta.blog.entity.User;

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
	@Sql(scripts="/sql/test_db_schema.sql")
	public void PostNewUser() throws JsonMappingException, JsonProcessingException {
		User u = new User();
		Date date = new Date();
		u.setLogin("new_test_user" + new Timestamp(date.getTime()));
		u.setPassword("new_test_user");
		u.setPosts(new ArrayList<Post>());
		Role r = new Role("ROLE_USER");
		r.setId(2L);
		
//		Set<Role> roles = new HashSet<Role>();
//		roles.add(r);
//		u.setRoles(roles);
		
//		System.out.println(objectMapper.writeValueAsString(u));
		
		ResponseEntity response = restTemplate.postForEntity("/user", u, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		User newUser = objectMapper.readValue(response.getBody().toString(), User.class); 
		assertNotEquals(0, newUser.getId());
		assertNotEquals(null, newUser.getId());
	}
	
	@Test
	@Sql(scripts="/sql/test_db_schema.sql")
	public void updateUser() throws JsonProcessingException, JSONException {
		User u = new User();
		u.setLogin("test_user-EDITED");
		u.setPassword("test_user-EDITED");
		u.setId(1L);
		u.setLocked(false);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String encodedUser = objectMapper.writeValueAsString(u);
		HttpEntity httpEntity = new HttpEntity<>(encodedUser, headers);
		
		ResponseEntity response = restTemplate.exchange("/user", HttpMethod.PUT, httpEntity, String.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(encodedUser, response.getBody().toString(), false);
	}
	
	@Test
	@Sql(scripts="/sql/test_db_schema.sql")
	public void deleteUser() {
		
		ResponseEntity response = restTemplate.exchange("/user/1", HttpMethod.DELETE, new HttpEntity(null, new HttpHeaders()), String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
