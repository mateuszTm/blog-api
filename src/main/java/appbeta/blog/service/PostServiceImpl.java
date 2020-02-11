package appbeta.blog.service;

import java.util.List;

import appbeta.blog.dao.PostRepository;
import appbeta.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Override
	@Transactional
	public void save(Post post) {
		postRepository.save(post);
	}

	@Override
	@Transactional
	public void remove(Post post) {
		postRepository.delete(post);
		
	}

	@Override
	@Transactional
	public List<Post> getAll() {
		return postRepository.findAll();
	}
}
