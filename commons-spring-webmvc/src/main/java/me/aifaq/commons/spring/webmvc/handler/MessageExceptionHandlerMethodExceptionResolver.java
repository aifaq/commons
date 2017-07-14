package me.aifaq.commons.spring.webmvc.handler;

import me.aifaq.commons.lang.exception.MessageException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 19:18 2017/6/19
 */
public class MessageExceptionHandlerMethodExceptionResolver
		extends AbstractHandlerMethodExceptionResolver {
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request,
			HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) {
		if (!(ex instanceof MessageException)) {
			return null;
		}
		final MessageException me = (MessageException) ex;

		final String message = messageSource.getMessage(me.getCode(), me.getArgs(), me.getMessage(),
				LocaleContextHolder.getLocale());

		final Map<String, Object> model = new HashMap<>();
		model.put("code", me.getCode());
		model.put("message", message);

		return new ModelAndView(new MappingJackson2JsonView(), model);
	}
}
