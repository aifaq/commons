package me.aifaq.commons.spring.example.service;

import me.aifaq.commons.spring.example.entity.Example;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author Wang Wei
 * @since 16:39 2017/6/16
 */
@ContextConfiguration(locations = { "classpath:META-INF/applicationContext.xml" })
public class ExampleServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	ExampleService exampleService;

	@Test
	public void testSave() {
		exampleService.save(new Example());
	}

	@Test
	public void testInsert() {
		exampleService.insert(new Example());
	}
}
