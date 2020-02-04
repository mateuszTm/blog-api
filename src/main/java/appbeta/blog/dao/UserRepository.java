package appbeta.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import appbeta.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
