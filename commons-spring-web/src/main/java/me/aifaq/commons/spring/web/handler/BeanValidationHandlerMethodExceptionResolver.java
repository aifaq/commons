package me.aifaq.commons.spring.web.handler;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 19:51 2017/6/19
 */
public class BeanValidationHandlerMethodExceptionResolver extends AbstractHandlerMethodExceptionResolver {
    private static final String BEAN_VALIDATION = "BEAN_VALIDATION";

    private Object code = BEAN_VALIDATION;

    static String getErrorMessage(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return null;
        }
        final FieldError fieldError = bindingResult.getFieldError();
        if (fieldError == null) {
            final ObjectError objectError = bindingResult.getGlobalError();
            if (objectError == null) {
                return null;
            }
            return objectError.getDefaultMessage();
        } else {
            return fieldError.getDefaultMessage();
        }
    }

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception ex) {
        final String message;
        if (ex instanceof BindException) {
            final BindException be = (BindException) ex;

            message = getErrorMessage(be);
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
        } else if (ex instanceof MethodArgumentNotValidException) {
            final MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;

            message = getErrorMessage(methodArgumentNotValidException.getBindingResult());
        } else {
            return null;
        }

        return new ModelAndView(new MappingJackson2JsonView(), getModel(message));
    }

    protected Map<String, Object> getModel(String message) {
        final Map<String, Object> model = Maps.newHashMap();
        model.put("code", code);
        model.put("message", message);
        return model;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }
}
