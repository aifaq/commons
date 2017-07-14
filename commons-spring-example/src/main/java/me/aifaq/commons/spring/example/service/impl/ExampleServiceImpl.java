package me.aifaq.commons.spring.example.service.impl;

import me.aifaq.commons.lang.exception.MessageException;
import me.aifaq.commons.spring.example.advice.TransactionAdvice;
import me.aifaq.commons.spring.example.entity.Example;
import me.aifaq.commons.spring.example.service.ExampleService;
import org.springframework.stereotype.Service;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:19 2017/6/16
 */
@Service
public class ExampleServiceImpl implements ExampleService {
	@Override
	public Example get(Long id) {
		System.out.println(this.getClass().getSimpleName() + ".get()");

		if (id == 0) {
			return null;
		}
		final Example example = new Example();
		example.setId(id);
		if (example.getId() != 5) {
			example.setName("name" + id);
		}
		return example;
	}

	@TransactionAdvice.Transaction
	@Override
	public Integer insert(Example example) {
		System.out.println(this.getClass().getSimpleName() + ".insert()");
		return 1;
	}

	@TransactionAdvice.Transaction
	@Override
	public Integer update(Example example) {
		System.out.println(this.getClass().getSimpleName() + ".update()");
		if (example.getId() == 5) {
			throw new MessageException("5", "id must not be 5");
		}
		return 1;
	}

	@Override
	public Integer save(Example example) {
		System.out.println(this.getClass().getSimpleName() + ".save()");
		if (example.getId() == null) {
			return insert(example);
		}
		else {
			return update(example);
		}
	}
}
