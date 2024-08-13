package com.cgm.life.flyway;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;

@jakarta.enterprise.context.ApplicationScoped
public class FlywayRunner {

	@ConfigProperty(name = "wordlist.flyway.migrate")
	boolean runMigration;

	@ConfigProperty(name = "quarkus.datasource.jdbc.url")
	String datasourceUrl;
	@ConfigProperty(name = "quarkus.datasource.username")
	String datasourceUsername;
	@ConfigProperty(name = "quarkus.datasource.password")
	String datasourcePassword;

	public void runFlywayMigration(@Observes StartupEvent event) {
		if (runMigration) {
			Flyway flyway = Flyway.configure().dataSource(datasourceUrl, datasourceUsername, datasourcePassword).load();
			flyway.migrate();
		}
	}
}
