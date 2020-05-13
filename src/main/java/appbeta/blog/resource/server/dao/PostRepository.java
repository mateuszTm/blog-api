package appbeta.blog.resource.server.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import appbeta.blog.resource.server.entity.Post;
import appbeta.blog.resource.server.entity.User;

public interface PostRepository extends JpaRepository <Post, Long> {
	
	public List <Post> findAll ();
	
	public Page<Post> findAll(Pageable pageable);
	
	@Modifying
	@Query("delete from Post p where p.user.id=:id")
	public Integer deleteByUserId(Long id);
	
	@Modifying
	@Query("UPDATE Post p SET p.date=:date, p.title=:title, p.content=:content WHERE p.id=:id")
	public Integer updatePostFields(Long id, Timestamp date, String title, String content);
	
	public Page<Post> findByUser (Pageable pageable, User user);
}
