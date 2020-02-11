package appbeta.blog.service;

import java.util.List;

import appbeta.blog.entity.Post;

public interface PostService {
	
	public void save (Post post);
	
	public void remove (Post post);
	
	// TODO (some optional conditions, pagination etc.);
	public List <Post> getAll();
}
