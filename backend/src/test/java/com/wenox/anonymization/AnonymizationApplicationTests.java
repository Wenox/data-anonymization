package com.wenox.anonymization;

import com.wenox.anonymization.core.PostgreSQLSpecification;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class AnonymizationApplicationTests extends PostgreSQLSpecification {

	@Test
	void contextLoads() {
		System.out.println("contextLoads: host: " + container.getHost());
		System.out.println("contextLoads: jdbcUrl: " + container.getJdbcUrl());
		System.out.println("contextLoads: exposedPorts: " + container.getExposedPorts());
		System.out.println("contextLoads: username: " + container.getUsername());
		System.out.println("contextLoads: password: " + container.getPassword());
		System.out.println("contextLoads: docker image name: " + container.getDockerImageName());
	}
}
