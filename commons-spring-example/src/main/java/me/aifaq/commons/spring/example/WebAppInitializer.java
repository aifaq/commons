package me.aifaq.commons.spring.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;
import java.io.IOException;
import java.util.Arrays;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebMvcConfig.class };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		ShallowEtagHeaderFilter etagFilter = new ShallowEtagHeaderFilter();

		return new Filter[] { characterEncodingFilter, etagFilter };
	}

	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("defaultHtmlEscape", "true");
	}

	static class ConfigApplicationContextInitializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		private Logger LOG = LoggerFactory.getLogger(ConfigApplicationContextInitializer.class);

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			ConfigurableEnvironment env = applicationContext.getEnvironment();
			try {
				MutablePropertySources propertySources = env.getPropertySources();
				propertySources
						.addFirst(new ResourcePropertySource("classpath:filtered.properties"));
				LOG.info("filtered.properties loaded: {}",
						Arrays.toString(env.getActiveProfiles()));
			}
			catch (IOException e) {
				// it's ok if the file is not there. we will just log that info.
				LOG.info(
						"didn't find filtered.properties in classpath so not loading it in the AppContextInitialized");
			}
		}
	}
}