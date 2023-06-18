package me.aifaq.commons.spring.example;

import me.aifaq.commons.spring.web.handler.BeanValidationHandlerMethodExceptionResolver;
import me.aifaq.commons.spring.web.handler.MessageExceptionHandlerMethodExceptionResolver;
import me.aifaq.commons.spring.web.page.SortConfigurableHandlerMethodArgumentResolver;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Import({ExampleConfig.class})
@ComponentScan(basePackages = {"me.aifaq.commons.spring.example.controller"}
        //, includeFilters = @Filter({Controller.class, ControllerAdvice.class }), useDefaultFilters = false
)
class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = super
                .requestMappingHandlerMapping(contentNegotiationManager, conversionService, resourceUrlProvider);
        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
        requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
        return requestMappingHandlerMapping;
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE);
        messageSource.setCacheSeconds(5);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean(name = "messageSourceAccessor")
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
        addDefaultHttpMessageConverters(converters);
    }

    @Bean
    MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
        supportedMediaTypes.add(new MediaType("text", "html", StandardCharsets.UTF_8));
        converter.setSupportedMediaTypes(supportedMediaTypes);
        return converter;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.getValidationPropertyMap().put(HibernateValidatorConfiguration.FAIL_FAST, "true");
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Bean
    MethodValidationPostProcessor methodValidationPostProcessor() {
        final MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator());
        return processor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver(new SortConfigurableHandlerMethodArgumentResolver()));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Override
    protected void configureHandlerExceptionResolvers(
            List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new BeanValidationHandlerMethodExceptionResolver());
        exceptionResolvers.add(messageExceptionHandlerMethodExceptionResolver());
    }

    @Bean
    MessageExceptionHandlerMethodExceptionResolver messageExceptionHandlerMethodExceptionResolver() {
        MessageExceptionHandlerMethodExceptionResolver resolver = new MessageExceptionHandlerMethodExceptionResolver();
        resolver.setMessageSource(messageSource());
        return resolver;
    }
}
