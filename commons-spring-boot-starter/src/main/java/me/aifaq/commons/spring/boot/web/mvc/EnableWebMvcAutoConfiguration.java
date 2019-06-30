package me.aifaq.commons.spring.boot.web.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 17:04 2019/6/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@Import(DefaultWebMvcConfigurer.class)
public @interface EnableWebMvcAutoConfiguration {
}
