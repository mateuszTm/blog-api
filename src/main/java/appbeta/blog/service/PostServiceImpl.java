package appbeta.blog.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import appbeta.blog.dao.PostRepository;
import appbeta.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Override
	@Transactional
	public void add(Post post) {
		post.setId(null);
		postRepository.save(post);
	}
	
	@Override
	@Transactional
	public void update(Post post) {
		if (postRepository.existsById(post.getId())) {
			postRepository.save(post);
		} else {
			throw new EntityNotFoundException("Post id " + post.getId() + " has not been found");
		}
	}
	
	@Override
	@Transactional
	public void updatePostFields(Long id, String title, String content) {
		if (postRepository.existsById(id)) {
			Date date = new Date();
			postRepository.updatePostFields(id, new Timestamp(date.getTime()), title, content);
		} else {
			throw new EntityNotFoundException("Post id " + id + " has not been found");
		}
	}

	@Override
	@Transactional
	public void remove(Post post) {
		postRepository.delete(post);	
	}
	
	@Override
	@Transactional
	public void removeById(Long id) {
		postRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public void removeByUserId(Long id) {
		postRepository.deleteByUserId(id);
	}

	@Override
	@Transactional
	public List<Post> getAll() {
		return postRepository.findAll();
	}
	
	@Override
	@Transactional
	public Page<Post> getAll(Pageable pageable) {
		return postRepository.findAll(pageable);
	}
	
	@Override
	@Transactional
	public Post getById(Long id) {
		return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post id " + id + " has not been found"));
	}
}
