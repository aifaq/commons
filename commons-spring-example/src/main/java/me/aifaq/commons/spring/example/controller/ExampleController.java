package me.aifaq.commons.spring.example.controller;

import me.aifaq.commons.lang.bean.CommonReturnDto;
import me.aifaq.commons.lang.validation.group.Save;
import me.aifaq.commons.spring.example.entity.Example;
import me.aifaq.commons.spring.example.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 20:15 2017/6/19
 */
@Controller
@RequestMapping("/example")
public class ExampleController {
	@Autowired
	ExampleService exampleService;

	@RequestMapping("/get")
	@ResponseBody
	public CommonReturnDto<?> save(Long id) {
		return new CommonReturnDto<>(exampleService.get(id));
	}

	@RequestMapping("/save")
	@ResponseBody
	public CommonReturnDto<?> save(@Validated({ Save.class }) Example example) {
		return new CommonReturnDto<>(exampleService.save(example));
	}

	@RequestMapping("/update")
	@ResponseBody
	public CommonReturnDto<?> update(Example example) {
		return new CommonReturnDto<Integer>(exampleService.update(example));
	}

	@RequestMapping("/insert")
	@ResponseBody
	public CommonReturnDto<?> insert(Example example) {
		return new CommonReturnDto<>(exampleService.insert(example));
	}
}
