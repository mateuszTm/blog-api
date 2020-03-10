package appbeta.blog.controller;

import org.springframework.web.bind.annotation.RestController;

import appbeta.blog.dto.AddPostForm;
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

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public PostForm add(@Valid @RequestBody AddPostForm postForm) {
		Post post = new Post(
						new Timestamp(new Date().getTime()),
						postForm.getTitle(),
						postForm.getContent()
					);
		post.setUser(userService.findUserById(postForm.getUserId()));
		postService.add(post);
		return new PostForm(post);
	}
	
	@PutMapping("/{id}")
	public PostForm edit(@Valid @RequestBody PostForm postForm, @PathVariable long id) {
		postService.updatePostFields(
				id, 
				postForm.getTitle(), 
				postForm.getContent());
		return new PostForm(postService.getById(id));
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
