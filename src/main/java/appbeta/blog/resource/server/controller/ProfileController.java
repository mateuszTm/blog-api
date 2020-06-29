package appbeta.blog.resource.server.controller;

import org.springframework.web.bind.annotation.RestController;

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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
		return profileService.getById(id);
	}
	
	@GetMapping()
	public Profile getSelf(Principal principal) {
		return profileService.getByLogin(principal.getName());
	}
	
	@GetMapping("/list")
	public Page <Profile> getPage(Pageable pageable) {
		return profileService.getAll(pageable);
	}
	
	@PostMapping()
	public Profile add(@Valid @RequestBody Profile profile, HttpServletRequest request) throws Exception {
		profileService.add(profile);
		return profile;
	}
	
	
	@PutMapping("/{id}")
	public Profile edit(@Valid @RequestBody EditProfileForm profileForm, @PathVariable long id) {
		Profile profile = profileService.getById(id);
		profile.setDescription(profileForm.getDescription());
		profile.setActive(profileForm.getActive());
		profileService.updateProfile(profile);
		return profile;
	}
	
	@PutMapping
	public Profile editSelf(@Valid @RequestBody EditProfileForm profileForm, Principal principal) {
		Profile profile = profileService.getByLogin(principal.getName());
		profile.setDescription(profileForm.getDescription());
		profile.setActive(profileForm.getActive());
		profileService.updateProfile(profile);
		return profile;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		profileService.removeById(id);
	}
	
	@DeleteMapping
	public void deleteSelf(Principal principal) {
		Profile profile = profileService.getByLogin(principal.getName());
		profileService.remove(profile);
	}
	
	@GetMapping("/post")
	public Page<PostForm> getUserPosts(Pageable pageable, Principal principal){
		return postService.getByProfile(
				pageable,
				profileService.getByLogin(principal.getName())
			).map((Post post) -> new PostForm(post));
	}
}
