package com.globerce.management.config.persistenceConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(
        entityManagerFactoryRef = "dataEntityManagerFactory",
        transactionManagerRef = "dataEntityManager",
        basePackages = {"com.globerce.management.repository.dataRepository"})
public class DataPersistenceConfig extends BasePersistenceConfig {

    @Bean
    @ConfigurationProperties(prefix = "data.datasource")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dataEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return getEntityManagerFactory(secondDataSource(), "com.globerce.management.entity.data");
    }

    @Bean(name = "dataEntityManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
}
