package appbeta.blog.resource.server.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import appbeta.blog.resource.server.dto.EditProfileForm;
import appbeta.blog.resource.server.dto.PostForm;
import appbeta.blog.resource.server.entity.Post;
import appbeta.blog.resource.server.entity.Profile;
import appbeta.blog.resource.server.service.PostService;
import appbeta.blog.resource.server.service.ProfileService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/{id}")
	public Profile get(@PathVariable Long id) {
		return profileService.findProfileById(id);
	}
	
	@GetMapping()
	public Profile getSelf(Principal principal) {
		return profileService.findProfileByLogin(principal.getName());
	}
	
	@GetMapping("/list")
	public Page <Profile> getPage(Pageable pageable) {
		return profileService.getAllProfiles(pageable);
	}
	
	@PostMapping()
	public Profile add(@Valid @RequestBody Profile profile, HttpServletRequest request) throws Exception {
		profileService.add(profile);
		return profile;
	}
	
	
	@PutMapping("/{id}")
	public Profile edit(@Valid @RequestBody Profile profile, @PathVariable long id) {
		profile.setId(id);
		profileService.updateProfile(profile);
		return profile;
	}
	
	@PutMapping
	public Profile editSelf(@Valid @RequestBody EditProfileForm editProfileForm, Principal principal) {
		Profile profile = profileService.findProfileByLogin(principal.getName());
		profile.setDescription(editProfileForm.getdescription());
		profileService.updateProfile(profile);
		return profile;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		profileService.removeById(id);
	}
	
	@DeleteMapping
	public void deleteSelf(Principal principal) {
		Profile profile = profileService.findProfileByLogin(principal.getName());
		profileService.remove(profile);
	}
	
	@GetMapping("/post")
	public Page<PostForm> getUserPosts(Pageable pageable, Principal principal){
		return postService.getByProfile(
				pageable,
				profileService.findProfileByLogin(principal.getName())
			).map((Post post) -> new PostForm(post));
	}
}
