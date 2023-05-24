package com.auth.opinionscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.auth.opinionscope.repository")
@EntityScan("com.auth.opinionscope.model")
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class OpinionScopeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpinionScopeApplication.class, args);
	}

}
