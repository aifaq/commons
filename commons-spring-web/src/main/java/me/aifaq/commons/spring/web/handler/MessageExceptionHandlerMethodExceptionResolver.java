package me.aifaq.commons.spring.web.handler;

import com.google.common.collect.Maps;
import me.aifaq.commons.lang.exception.MessageException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 19:18 2017/6/19
 */
public class MessageExceptionHandlerMethodExceptionResolver extends AbstractHandlerMethodExceptionResolver {
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) {
		if (!(ex instanceof MessageException)) {
			return null;
		}
		final MessageException me = (MessageException) ex;

		final String message;
		if (messageSource == null) {
			message = ex.getMessage();
		} else {
			message = messageSource.getMessage(me.getCode(), me.getArgs(), me.getMessage(), LocaleContextHolder.getLocale());
		}

		return new ModelAndView(new MappingJackson2JsonView(), getModel(me.getCode(), message));
	}

	protected Map<String, Object> getModel(String code, String message) {
		final Map<String, Object> model = Maps.newHashMap();
		model.put("code", code);
		model.put("message", message);
		return model;
	}
}
