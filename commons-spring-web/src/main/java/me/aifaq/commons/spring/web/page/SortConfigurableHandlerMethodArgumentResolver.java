package me.aifaq.commons.spring.web.page;

import me.aifaq.commons.lang.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 固定排序字段可选范围，防止非法传入
 *
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:19 2017/7/23
 */
public class SortConfigurableHandlerMethodArgumentResolver extends SortHandlerMethodArgumentResolver {
	@Override
	public Sort resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		final Sort sort = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
		if (sort == null || !sort.iterator().hasNext()) {
			return sort;
		}

		final SortConfiguration sortConfiguration = parameter.getParameterAnnotation(SortConfiguration.class);
		if (sortConfiguration == null) {
			return sort;
		}
		final String[] allowSorts = sortConfiguration.allowSorts();
		final boolean allowAll = sortConfiguration.emptyAllowAll() && ArrayUtils.isEmpty(allowSorts);

		final String[] denySorts = sortConfiguration.denySorts();

		for (Sort.Order order : sort) {
			if (ArrayUtil.containsIgnoreCase(denySorts, order.getProperty())) {
				throw new IllegalArgumentException(String.format("不支持的排序：%s", order));
			}

			if (!allowAll && !ArrayUtil.containsIgnoreCase(allowSorts, order.getProperty())) {
				throw new IllegalArgumentException(String.format("不支持的排序：%s", order));
			}
		}

		return sort;
	}
}
