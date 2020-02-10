package appbeta.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import appbeta.blog.dao.RoleRepository;
import appbeta.blog.dao.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class BlogApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
		
	}
}	

