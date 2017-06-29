package me.aifaq.commons.spring.webmvc.handler;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Wang Wei
 * @since 19:51 2017/6/19
 */
public class BeanValidationHandlerMethodExceptionResolver
		extends AbstractHandlerMethodExceptionResolver {
	private static final String BEAN_VALIDATION = "BEAN_VALIDATION";

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request,
			HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) {
		final String message;
		if (ex instanceof BindException) {
			final BindException be = (BindException) ex;

			message = be.getFieldError().getDefaultMessage();
		} else if (ex instanceof ConstraintViolationException) {
			final ConstraintViolationException cve = (ConstraintViolationException) ex;

			final Set<ConstraintViolation<?>> constraintViolationSet = cve.getConstraintViolations();
			if (CollectionUtils.isEmpty(constraintViolationSet)) {
				message = cve.getMessage();
			} else {
				final Iterator<ConstraintViolation<?>> iterator = constraintViolationSet.iterator();
				final ConstraintViolation<?> constraintViolation = iterator.next();
				message = constraintViolation.getMessage();
			}
		} else {
			return null;
		}
		final Map<String, Object> model = new HashMap<>();
		model.put("code", BEAN_VALIDATION);
		model.put("message", message);

		return new ModelAndView(new MappingJackson2JsonView(), model);
	}
}
