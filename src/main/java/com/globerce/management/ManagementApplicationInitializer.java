package com.globerce.management;

import com.globerce.management.config.RootConfig;
import com.globerce.management.config.SecurityConfig;
import com.globerce.management.config.WebConfig;
import com.globerce.management.config.persistenceConfig.DataPersistenceConfig;
import com.globerce.management.config.persistenceConfig.SystemPersistenceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ManagementApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class, SecurityConfig.class, DataPersistenceConfig.class, SystemPersistenceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
