package me.aifaq.commons.spring.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Wang Wei
 * @since 20:10 2017/6/19
 */
@Configuration
@ImportResource({ "classpath:/META-INF/applicationContext.xml" })
@ComponentScan(basePackages = { "me.aifaq.commons.spring.example" })
public class ExampleConfig {
}
