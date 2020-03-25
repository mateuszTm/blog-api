package appbeta.blog.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
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
	
	protected void assumeThatUserIsTheAuthorOrAdmin(Post post, HttpServletRequest request) throws Exception {
		if (!(post.getUser().getLogin().equals(request.getUserPrincipal().getName())) && !request.isUserInRole("ROLE_ADMIN")) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not the author of this post");
		}
	}
	
	@PostMapping
	public PostForm add(@Valid @RequestBody AddPostForm addPostForm, Principal principal) {
		Post post = new Post(
						new Timestamp(new Date().getTime()),
						addPostForm.getTitle(),
						addPostForm.getContent()
					);
		post.setUser(userService.findUserByLogin(principal.getName()));
		postService.add(post);
		return new PostForm(post);
	}
	
	@PutMapping("/{id}")
	public PostForm edit(@Valid @RequestBody PostForm postForm, @PathVariable long id, HttpServletRequest request) throws Exception {
		Post post = postService.getById(id);
		assumeThatUserIsTheAuthorOrAdmin(post, request);
		post.setTitle(postForm.getTitle());
		post.setContent(postForm.getContent());
		postService.update(post);
		return new PostForm(post);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id, HttpServletRequest request) throws Exception{
		Post post = postService.getById(id);
		assumeThatUserIsTheAuthorOrAdmin(post, request);
		postService.remove(post);
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
