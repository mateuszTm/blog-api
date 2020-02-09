package appbeta.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import appbeta.blog.entity.Post;

public interface PostRepository extends JpaRepository <Post, Long> {

}
