package com.example.configurable.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("local")
public class H2ServerConfiguration {


	/**
	 * @see org.h2.server.TcpServer
	 * @return
	 * @throws SQLException
	 */
	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource dataSource() throws SQLException {
			Server server = Server.createTcpServer(
					"-tcp",
					"-tcpAllowOthers",
					"-ifNotExists",
					"-tcpPort", "9093", "-key", "mem:management_db_9093", "~/configurable_project").start();

		if(server.isRunning(true)){
			log.info("server run success");
		}
		log.info("h2 server url = {}", server.getURL());

		return new org.apache.tomcat.jdbc.pool.DataSource();
	}


}

