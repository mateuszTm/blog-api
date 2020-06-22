package appbeta.blog.resource.server.functional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts="classpath:sql/test_data.sql")
public class ProfileTest extends AbstractFunctional {
	
	private String url = "/profile";
	
	private ObjectNode getUserProfileResponse() {
		return getJsonObj()
			.put("id", 2)
			.put("login", "test_user")
			.put("description", "")
			.put("active", true);
	}
	
	@Test
	public void addProfileAsAdmin() throws Exception {
		String json = getJsonObj()
			.put("login", "test")
			.put("description", "test desc")
			.toString();
		
		String expected = getJsonObj()
			.put("login", "test")
			.put("description", "test desc")
			.put("active", false)
			.toString();
		
		mockMvc.perform(
				post(url)
				.with(getAdmin())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(expected))
			.andExpect(jsonPath("$.id").isNumber());
	}
	
	@Test
	public void addProfileAsUser() throws Exception {
		String json = getJsonObj()
				.put("login", "test")
				.put("description", "test desc")
				.toString();
		
		mockMvc.perform(
				post(url)
				.with(getUser())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test 
    public void failToAddProfileAsUnauthorized() throws Exception {
		String json = getJsonObj()
				.put("login", "test login")
				.put("desciption", "Lorem ipsum dolor sit amet")
				.toString();
		
		mockMvc.perform(
    			post("/profile")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(json))
    		.andDo(print())
    		.andExpect(status().isForbidden());
	}
	
	// get
	@Test
	public void getCurrentUserProfile() throws Exception{
		String expected = getUserProfileResponse().toString();
		
		mockMvc.perform(
				get(url)
				.with(getUser()))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(expected));
	}

	@Test 
    public void failToGetCurrentProfileAsUnauthorized() throws Exception {
    	mockMvc.perform(
    			get(url))
    		.andDo(print())
    		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void failToGetOtherUserProfileAsUser() throws Exception {
		mockMvc.perform(
				get(url + "/" + getUserProfileResponse().get("id"))
				.with(getUser()))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void getOtherUserProfileAsAdmin() throws Exception {
		ObjectNode expected = getUserProfileResponse();
		
		mockMvc.perform(
				get(url + "/" + expected.get("id"))
				.with(getAdmin()))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(expected.toString()));
	}
	
	// get list
	@Test
	public void getProfilesListAsAdmin() throws Exception{
		ObjectNode listObj = getJsonObj();
		listObj.putArray("content");
		listObj.withArray("content")
				.add(
						getJsonObj()
						.put("id", 1)
						.put("login", "test_admin")
						.put("description", "")
						.put("active", true))
				.add(
						getUserProfileResponse()
				);
						
		
		mockMvc.perform(
				get(url + "/list")
				.with(getAdmin()))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(listObj.toString()));
	}
	
	// fail to get list
	@Test
	public void failToGetProfilesListAsUser() throws Exception {
		mockMvc.perform(
				get(url + "/list")
				.with(getUser()))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test 
    public void failToGetProfilesListAsUnauthorized() throws Exception {
    	mockMvc.perform(
    			get(url + "/list"))
    		.andDo(print())
    		.andExpect(status().isUnauthorized());
	}
	
	// edit
	@Test
	public void editCurrentUserProfileAsUser() throws Exception {
		String json = getJsonObj()
				.put("description", "edited description")
				.put("active", false)
				.toString();
		
		String expected = getUserProfileResponse()
				.put("description", "edited description")
				.put("active", false)
				.toString();
		
		mockMvc.perform(
				put(url)
				.with(getUser())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(expected));
	}
	
	@Test
	public void failToEditOtherUserProfileAsUser() throws Exception {
		String json = getJsonObj()
				.put("description", "edited description")
				.put("active", true)
				.toString();
		
		mockMvc.perform(
				put(url + "/" + adminId)
				.with(getUser())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andDo(print())
		.andExpect(status().isForbidden());
	}

	@Test
	public void editOtherUserProfileAsAdmin() throws Exception {
		String json = getJsonObj()
				.put("description", "edited description")
				.put("active", false)
				.toString();
		
		mockMvc.perform(
				put(url + "/" + getUserProfileResponse().get("id"))
				.with(getAdmin())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().json(json));
	}
	
	// delete
	@Test
	public void deleteOwnProfileAsAdmin() throws Exception {
		mockMvc.perform(
				delete(url)
				.with(getAdmin()))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	public void deleteOwnProfileAsUser() throws Exception {
		mockMvc.perform(
				delete(url)
				.with(getUser()))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	public void deleteOtherUserProfileAsAdmin() throws Exception {
		mockMvc.perform(
				delete(url + "/" + getUserProfileResponse().get("id"))
				.with(getAdmin()))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	public void failToDeleteOtherUserProfileAsUser() throws Exception {
		mockMvc.perform(
				delete(url + "/" + adminId)
				.with(getUser()))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
}
