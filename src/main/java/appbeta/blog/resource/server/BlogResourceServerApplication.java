package appbeta.blog.resource.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import appbeta.blog.resource.server.dao.RoleRepository;
import appbeta.blog.resource.server.dao.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class BlogResourceServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BlogResourceServerApplication.class, args);
		
	}
}	

