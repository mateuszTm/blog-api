package appbeta.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import appbeta.blog.entity.Post;

public interface PostRepository extends JpaRepository <Post, Long> {
	
	public List <Post> findAll ();
}
