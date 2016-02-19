package com.StudShare.config;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;


@Configuration
public class HibernateConfig
{
    @Value("${jdbc.driverClassName}")
    private String DRIVER_CLASS_NAME;
    @Value("${jdbc.url}")
    private String URL;
    @Value("${jdbc.user}")
    private String USER;
    @Value("${jdbc.password}")
    private String PASSWORD;
    @Value("${hibernate.hbm2ddl.auto}")
    private String SCHEMA_MODE;
    @Value("${hibernate.dialect}")
    private String DIALECT;
    @Value("${hibernate.show_sql}")
    private String SHOW_SQL;


    @Bean(name = "dataSource")
    public DataSource getDataSource()
    {

        DataSource dataSource = new DataSource();

        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaxWait(100000);
        dataSource.setMaxIdle(25);
        dataSource.setMinIdle(5);
        return dataSource;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory()
    {

        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(getDataSource());
        sessionBuilder.setProperty("hibernate.hbm2ddl.auto", SCHEMA_MODE);
        sessionBuilder.setProperty("hibernate.dialect", DIALECT);
        sessionBuilder.setProperty("hibernate.show_sql", SHOW_SQL);
        sessionBuilder.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");


        sessionBuilder.scanPackages("com.StudShare.domain");

        return sessionBuilder.buildSessionFactory();
    }


    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager()
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(getSessionFactory());

        return transactionManager;
    }
}
