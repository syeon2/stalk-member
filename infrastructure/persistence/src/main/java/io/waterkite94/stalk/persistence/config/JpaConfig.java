package io.waterkite94.stalk.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = {"io.waterkite94.stalk.persistence"})
@EnableJpaRepositories(basePackages = {"io.waterkite94.stalk.persistence"})
public class JpaConfig {
}
