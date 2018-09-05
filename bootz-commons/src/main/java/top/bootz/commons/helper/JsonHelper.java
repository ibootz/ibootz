package top.bootz.commons.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import top.bootz.commons.exception.BaseRuntimeException;

public final class JsonHelper {

    public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonHelper() {
    }

    static {
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT_DATETIME));
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T fromJSON(String json, Class<T> beanClass) {
        return JsonHelper.fromJSON(json, beanClass, ArrayUtils.EMPTY_CLASS_ARRAY);
    }

    /**
     * @param json           ： json源文件
     * @param beanClass      ： 转换目标类
     * @param elementClasses ： 复杂对象的转换
     * @return
     * @throws Exception
     */
    public static <T> T fromJSON(String json, Class<T> beanClass, Class<?>... elementClasses) {
        try {
            return elementClasses == null || elementClasses.length == 0 ? objectMapper.readValue(json, beanClass)
                    : objectMapper.readValue(json,
                            objectMapper.getTypeFactory().constructParametricType(beanClass, elementClasses));
        } catch (IOException e) {
            throw new BaseRuntimeException(e.getMessage(), e);
        }
    }

    public static <T> String toJSON(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new BaseRuntimeException(e.getMessage(), e);
        }
    }

    public static boolean isJsonStr(String content) {
        boolean isJsonStr;
        try {
            getJSONObject(content);
            isJsonStr = true;
        } catch (Exception e) {
            isJsonStr = false;
        }

        if (!isJsonStr) {
            try {
                getJSONArray(content);
                isJsonStr = true;
            } catch (Exception e) {
                isJsonStr = false;
            }
        }
        return isJsonStr;
    }

    public static JSONObject getJSONObject(String payload) {
        return JSONObject.parseObject(payload);
    }

    public static JSONArray getJSONArray(String payload) {
        return JSONArray.parseArray(payload);
    }

}
