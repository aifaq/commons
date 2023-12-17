package me.aifaq.commons.spring.example.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 18:56 2017/6/16
 */
@Service
public class TransactionAdvice implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println(this.getClass().getSimpleName() + ".invoke()");


		Validated validated = invocation.getMethod().getAnnotation(Validated.class);
		System.out.println(validated);

		return invocation.proceed();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Transaction {
	}
}
