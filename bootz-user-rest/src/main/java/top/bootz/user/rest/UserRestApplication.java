package top.bootz.user.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class UserRestApplication {

	public static void main(String[] args) {
		log.debug("test logback log ... ");
		SpringApplication.run(UserRestApplication.class, args);
	}

}
