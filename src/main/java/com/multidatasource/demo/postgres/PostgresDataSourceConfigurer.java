package com.multidatasource.demo.postgres;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableJpaRepositories(
        basePackages = {"com.multidatasource.demo.postgres"},
        transactionManagerRef = "postgreSQLTransactionManager",
        entityManagerFactoryRef = "postgreSQLEntityManagerFactory"
)
@Configuration
@EnableTransactionManagement
public class PostgresDataSourceConfigurer {
    @Value("${postgres.dialect}")
    private String hibernateDialectClazzName;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties postgreSQLDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource postgreSQLDataSource(@Qualifier("postgreSQLDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgreSQLEntityManagerFactory(@Qualifier("postgreSQLDataSource") DataSource hubDataSource, EntityManagerFactoryBuilder builder) {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", hibernateDialectClazzName);
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        return builder.dataSource(hubDataSource).properties(properties).packages("com.multidatasource.demo.postgres")
                .persistenceUnit("postgreSQL").build();
    }

    @Bean
    public PlatformTransactionManager postgreSQLTransactionManager(@Qualifier("postgreSQLEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}


