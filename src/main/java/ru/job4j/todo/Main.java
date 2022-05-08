package ru.job4j.todo;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 06/04/2022 - 13:59
 */
@SpringBootApplication
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Bean
    public static SessionFactory sessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        LOG.info("Go to http://localhost:8080/index");
    }
}