package com.globerce.management.config;

        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.ComponentScan;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.core.io.ClassPathResource;
        import org.springframework.jdbc.datasource.init.DataSourceInitializer;
        import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
        import org.springframework.mail.javamail.JavaMailSender;
        import org.springframework.mail.javamail.JavaMailSenderImpl;

        import javax.sql.DataSource;
        import java.util.Properties;

@Configuration
@ComponentScan({"com.globerce.management.service", "com.globerce.management.repository"})
public class RootConfig {

    @Bean(value = "javaMailSender")
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("****");
        mailSender.setPassword("****");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;

    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier("firstDataSource") final DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/initialData.sql"));
        resourceDatabasePopulator.setSqlScriptEncoding("UTF-8");
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

}
