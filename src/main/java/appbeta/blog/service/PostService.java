package appbeta.blog.service;

import java.util.List;

import appbeta.blog.entity.Post;
import appbeta.blog.entity.User;

public interface PostService {
	
	public void save (Post post);
	
	public void remove (Post post);
	
	public List <Post> getAllUserPosts (User user);
	
	// TODO public List <Post> getAllPosts (some optional conditions, pagination etc.);
}
