package com.viseo.config;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ComponentScan({ "com.viseo" })
@Configuration
public class SpringRootConfig {

	@PostConstruct
	public void startDBManager() {
		// LANCE l'IHM pour executer des requetes tranquillement
		// La version xml existe dans spring-bean-config.xml
		DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", "" });
	}

}