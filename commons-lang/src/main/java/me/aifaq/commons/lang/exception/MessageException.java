package me.aifaq.commons.lang.exception;

import com.google.common.collect.Maps;
import me.aifaq.commons.lang.MapUtil;

import java.util.Map;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 11:18 2017/6/16
 */
public class MessageException extends RuntimeException {
    private final String code;
    private Object[] args;

    private Map<String, Object> attributes = Maps.newHashMap();

    public MessageException(String code, String message, Object... args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public MessageException attribute(String key, Object value) {
        attributes.put(key, value);
        return this;
    }

    public MessageException attributes(Map<String, Object> attributes) {
        this.setAttributes(attributes);
        return this;
    }

    public <T> T attribute(String key) {
        return (T) MapUtil.get(attributes, key);
    }


    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
