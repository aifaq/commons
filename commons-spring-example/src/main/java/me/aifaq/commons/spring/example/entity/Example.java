package me.aifaq.commons.spring.example.entity;

import jakarta.validation.constraints.NotNull;
import me.aifaq.commons.lang.validation.group.Create;
import me.aifaq.commons.lang.validation.group.Retrieve;
import me.aifaq.commons.lang.validation.group.Update;
import org.hibernate.validator.constraints.Length;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 16:18 2017/6/16
 */
public class Example {
	@NotNull(groups = { Retrieve.class, Update.class }, message = "{Example.id.NotNull}")
	private Long id;
	@NotNull(groups = { Retrieve.class, Create.class, Update.class }, message = "{Example.name.NotNull}")
	@Length(max = 16, groups = { Create.class, Update.class }, message = "{Example.name.Length}")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
