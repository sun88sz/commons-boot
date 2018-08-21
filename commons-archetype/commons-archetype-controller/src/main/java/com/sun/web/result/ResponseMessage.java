package com.sun.web.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 响应消息。controller中处理后，返回此对象，响应请求结果给客户端。
 * <p>
 * 只要正常返回（即使 ResponseMessage.error ）那么 http请求 的 status=200
 * 但 ResponseMessage.error 的返回中 status 不同
 *
 * @author Sun
 */
@ApiModel(description = "响应结果")
@NoArgsConstructor
public class ResponseMessage<T> implements Serializable {

    private static ObjectMapper mapper = new ObjectMapper();

    @Getter
    @ApiModelProperty(value = "异常码")
    private String code;

    @Getter
    @ApiModelProperty("调用结果消息")
    protected String message;

    @Getter
    @ApiModelProperty("成功时响应数据")
    protected T result;

    @Getter
    @ApiModelProperty(value = "接口状态码", required = true)
    protected Integer status;

    @Getter
    @ApiModelProperty(value = "时间戳", required = true, dataType = "Long")
    private Long timestamp;

    /**
     * 过滤字段：指定需要序列化的字段
     */
    @Getter
    @ApiModelProperty(hidden = true)
    private transient Map<Class<?>, Set<String>> includes;

    /**
     * 过滤字段：指定不需要序列化的字段
     */
    @Getter
    @ApiModelProperty(hidden = true)
    private transient Map<Class<?>, Set<String>> excludes;


    /**
     * 错误返回
     *
     * @param message
     * @return
     */
    public static <T> ResponseMessage<T> error(String message) {
        return error(ResultStatus.ERROR.getStatus(), message);
    }


    public static <T> ResponseMessage<T> error(String code, String message) {
        return new ResponseMessage<T>().message(message).status(ResultStatus.ERROR.getStatus()).code(code).putTimeStamp();
    }

    public static <T> ResponseMessage<T> error(int status, String code, String message) {
        return new ResponseMessage<T>().message(message).status(status).code(code).putTimeStamp();
    }

    public static <T> ResponseMessage<T> error(int status, String message) {
        return new ResponseMessage<T>().message(message).status(status).putTimeStamp();
    }


    /**
     * 正确返回
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> ok() {
        return ok(null);
    }

    public static <T> ResponseMessage<T> ok(T result) {
        return ok(result, null);
    }

    public static <T> ResponseMessage<T> ok(T result, String message) {
        return new ResponseMessage<T>().result(result).message(message).status(ResultStatus.OK.getStatus()).putTimeStamp();
    }


    public ResponseMessage<T> include(Class<?> type, String... fields) {
        return include(type, Arrays.asList(fields));
    }

    public ResponseMessage<T> include(Class<?> type, Collection<String> fields) {
        if (includes == null)
            includes = new HashMap<>();
        if (fields == null || fields.isEmpty()) return this;
        fields.forEach(field -> {
            if (field.contains(".")) {
                String tmp[] = field.split("[.]", 2);
                try {
                    Field field1 = type.getDeclaredField(tmp[0]);
                    if (field1 != null) {
                        include(field1.getType(), tmp[1]);
                    }
                } catch (Throwable e) {
                }
            } else {
                getStringListFromMap(includes, type).add(field);
            }
        });
        return this;
    }

    public ResponseMessage<T> exclude(Class type, Collection<String> fields) {
        if (excludes == null)
            excludes = new HashMap<>();
        if (fields == null || fields.isEmpty()) return this;
        fields.forEach(field -> {
            if (field.contains(".")) {
                String tmp[] = field.split("[.]", 2);
                try {
                    Field field1 = type.getDeclaredField(tmp[0]);
                    if (field1 != null) {
                        exclude(field1.getType(), tmp[1]);
                    }
                } catch (Throwable e) {
                }
            } else {
                getStringListFromMap(excludes, type).add(field);
            }
        });
        return this;
    }

    public ResponseMessage<T> exclude(Collection<String> fields) {
        if (excludes == null)
            excludes = new HashMap<>();
        if (fields == null || fields.isEmpty()) return this;
        Class type;
        if (getResult() != null) type = getResult().getClass();
        else return this;
        exclude(type, fields);
        return this;
    }

    public ResponseMessage<T> include(Collection<String> fields) {
        if (includes == null)
            includes = new HashMap<>();
        if (fields == null || fields.isEmpty()) return this;
        Class type;
        if (getResult() != null) type = getResult().getClass();
        else return this;
        include(type, fields);
        return this;
    }

    public ResponseMessage<T> exclude(Class type, String... fields) {
        return exclude(type, Arrays.asList(fields));
    }

    public ResponseMessage<T> exclude(String... fields) {
        return exclude(Arrays.asList(fields));
    }

    public ResponseMessage<T> include(String... fields) {
        return include(Arrays.asList(fields));
    }

    protected Set<String> getStringListFromMap(Map<Class<?>, Set<String>> map, Class type) {
        return map.computeIfAbsent(type, k -> new HashSet<>());
    }

    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "jackson convert error";
    }

    public ResponseMessage<T> status(int status) {
        this.status = status;
        return this;
    }

    public ResponseMessage<T> code(String code) {
        this.code = code;
        return this;
    }

    private ResponseMessage<T> message(String message) {
        this.message = message;
        return this;
    }

    private ResponseMessage<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ResponseMessage<T> result(T result) {
        this.result = result;
        return this;
    }


}