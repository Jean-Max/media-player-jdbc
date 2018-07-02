package com.viseo.config.db;


import com.viseo.dao.MusicResultSetExtractorDAO;
import com.viseo.dao.MusicRowMapperDAO;
import com.viseo.dao.impl.MusicResultSetExtractorDAOImpl;
import com.viseo.dao.impl.MusicRowMapperDAOImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Profile("hsql")
@Configuration
@PropertySource("classpath:application.properties")
public class HsqlDataSource {

	@Value("${hsql.jdbc.driver}")
	private String HSQLDB_JDBC_DRIVER;

	@Value("${hsql.url}")
	private String HSQLDB_URL;

	//@Value("${user.name}")// remplace la valeur dans le fichier properties par le nom du user dans c:utilisateur
	private String USERNAME = "sa";

	@Value("${password}")
	private String PASSWORD;

	@Value("${sql.script.create.db}")
	private String SQL_SCRIPT_CREATE_DB;

	@Value("${sql.script.insert.data}")
	private String SQL_SCRIPT_INSERT_DATA;

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties(){
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[]{new ClassPathResource("application.properties")};
		pspc.setLocations(resources);
		pspc.setIgnoreUnresolvablePlaceholders(true);
		return pspc;
	}


	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = initDataSource();

		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
		for (Resource resource : initSchema()) {
			resourceDatabasePopulator.addScript(resource);
		}
		DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);

		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource());
		return jdbcTemplate;
	}

	@Bean
	public MusicRowMapperDAO musicRowMapperDAO() {
		MusicRowMapperDAO musicRowMapperDAO = new MusicRowMapperDAOImpl();
		return musicRowMapperDAO;
	}

	@Bean
	public MusicResultSetExtractorDAO musicResultSetExtractorDAO() {
		MusicResultSetExtractorDAO musicResultSetExtractorDAO = new MusicResultSetExtractorDAOImpl();
		return musicResultSetExtractorDAO;
	}

	/**
	 * These are default values
	 * @return datasource
	 */
	private DriverManagerDataSource initDataSource () {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(HSQLDB_JDBC_DRIVER);
		dataSource.setUrl(HSQLDB_URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);
		return dataSource;
	}

	/**
	 * Create table and inserting values
	 */
	private List<Resource> initSchema () {
		List<Resource> resources = new ArrayList<>();
		resources.add(new ClassPathResource(SQL_SCRIPT_CREATE_DB));
		resources.add(new ClassPathResource(SQL_SCRIPT_INSERT_DATA));
		return resources;
	}

}