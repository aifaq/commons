package me.aifaq.commons.spring.boot.web.mvc;

import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.aifaq.commons.spring.converter.StringToCalendarConverter;
import me.aifaq.commons.spring.converter.StringToDateConverter;
import me.aifaq.commons.spring.converter.StringToTimestampConverter;
import me.aifaq.commons.spring.web.handler.BeanValidationHandlerMethodExceptionResolver;
import me.aifaq.commons.spring.web.handler.MessageExceptionHandlerMethodExceptionResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Map;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 17:04 2019/6/24
 */
@Configuration
public class DefaultWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    protected MessageSource messageSource;

    @Value("${aifaq.message.code.fileSizeLimitExceeded:FILE_SIZE_LIMIT_EXCEEDED}")
    private String fileSizeLimitExceeded;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCalendarConverter());
        registry.addConverter(new StringToDateConverter());
        registry.addConverter(new StringToTimestampConverter());
    }

    @Override
    public Validator getValidator() {
        final Map<String, String> validationProperties = Maps.newHashMap();
        validationProperties.put(HibernateValidatorConfiguration.FAIL_FAST, "true");

        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        validator.setValidationPropertyMap(validationProperties);
        return validator;
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        final BeanValidationHandlerMethodExceptionResolver bvhmer = new BeanValidationHandlerMethodExceptionResolver();
        if (CollectionUtils.isEmpty(exceptionResolvers)) {
            exceptionResolvers.add(bvhmer);
        } else {
            exceptionResolvers.add(1, bvhmer);
        }

        final MessageExceptionHandlerMethodExceptionResolver mehmer = new MessageExceptionHandlerMethodExceptionResolver();
        mehmer.setMessageSource(messageSource);
        exceptionResolvers.add(mehmer);
        exceptionResolvers.add(new AbstractHandlerMethodExceptionResolver() {
            @Override
            protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) {
                if (ex instanceof MultipartException) {
                    final MultipartException me = (MultipartException) ex;
                    final Throwable t = ExceptionUtils.getRootCause(me);
                    if (t instanceof FileSizeLimitExceededException) {
                        final FileSizeLimitExceededException fe = (FileSizeLimitExceededException) t;

                        final String defaultMessage = String.format("文件%s太大，请重新上传", fe.getFileName());
                        final Map<String, Object> model = Maps.newHashMap();
                        model.put("code", fileSizeLimitExceeded);
                        model.put("message", messageSource.getMessage(fileSizeLimitExceeded, new Object[]{fe.getFileName()}, defaultMessage, LocaleContextHolder.getLocale()));

                        return new ModelAndView(new MappingJackson2JsonView(), model);
                    }
                }
                return null;
            }
        });
    }
}
