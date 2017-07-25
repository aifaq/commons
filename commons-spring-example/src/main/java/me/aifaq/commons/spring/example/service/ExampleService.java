package me.aifaq.commons.spring.example.service;

import me.aifaq.commons.lang.validation.group.Create;
import me.aifaq.commons.lang.validation.group.Retrieve;
import me.aifaq.commons.lang.validation.group.Update;
import me.aifaq.commons.spring.example.entity.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:17 2017/6/16
 */
@Validated
public interface ExampleService {
	@Validated({ Retrieve.class }) // 这里有点奇葩，这个注解放到入参，Bean Validation不认
	@Valid // 如果不加@Valid，则不会校验对象的内部field
	@NotNull(message = "{ExampleService.get.output0.NotNull}", groups = { Retrieve.class })
	Example get(@Valid @NotNull(message = "{ExampleService.get.input0.NotNull}", groups = {
			Retrieve.class }) Long id);

	@Validated({ Create.class })
	Integer insert(@Valid // 如果不加@Valid，则不会校验对象的内部field
	@NotNull(message = "{ExampleService.insert.input0.NotNull}", groups = {
			Create.class }) Example example);

	@Validated({ Update.class })
	Integer update(@Valid // 如果不加@Valid，则不会校验对象的内部field
	@NotNull(message = "{ExampleService.update.input0.NotNull}", groups = {
			Update.class }) Example example);

	Integer save(Example example);

	Page<Example> list(Pageable pageable);
}
