package appbeta.blog.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import appbeta.blog.dto.PostForm;
import appbeta.blog.entity.Post;
import appbeta.blog.service.PostService;
import appbeta.blog.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public PostForm add(@Valid @RequestBody PostForm postForm) {
		if (postForm.getUserId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Property 'userId' is required");
		}
		Post post = new Post(
					postForm.getDate(),
					postForm.getTitle(),
					postForm.getContent()
				);
		post.setUser(userService.findUserById(postForm.getUserId()));
		postService.save(post);
		return new PostForm(post);
	}
	
	@PutMapping
	public PostForm edit(@RequestBody @Valid PostForm postForm) {
		if (postForm.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Property 'id' is required");
		}
		postService.updatePostFields(
				postForm.getId(), 
				postForm.getTitle(), 
				postForm.getContent());
		return new PostForm(postService.getById(postForm.getId()));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		postService.removeById(id);
	}

	@GetMapping("/{id}")
	public PostForm get(@PathVariable Long id) {
		return new PostForm(postService.getById(id));
	}

	@GetMapping
	public Page<PostForm> getPage(Pageable pageable) {
		return postService.getAll(pageable)
				.map(post -> new PostForm((Post)post));
	}
}
