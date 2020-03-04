package appbeta.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import appbeta.blog.entity.Post;

public interface PostService {
	
	public void save (Post post);
	
	public void update(Post post);
	
	public void updatePostFields(Long id, String title, String content);
	
	public void remove (Post post);
	
	public void removeById(Long id);
	
	public void removeByUserId(Long id);
	
	public List <Post> getAll();
	
	public Page<Post> getAll(Pageable pageable);
	
	public Post getById(Long id);
}
