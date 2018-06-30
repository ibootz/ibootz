package top.bootz.commons.helper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapHelper {

    private MapHelper() {
    }

    public static Map<String, Object> emptyMap() {
        return new HashMap<>();
    }

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<Object, Object> toMap(T bean) {
        Map<Object, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object obj : beanMap.entrySet()) {
                @SuppressWarnings("unchecked")
                Entry<Object, Object> entry = (Entry<Object, Object>) obj;
                Object val = entry.getValue();
                if (val instanceof String) {
                    String str = (String) val;
                    if (StringUtils.isNotBlank(str)) {
                        map.put(entry.getKey(), str.trim());
                    }
                } else if (val != null) {
                    map.put(entry.getKey(), val);
                }
            }
        }
        return map;
    }

    /**
     * 将map装换为Javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T fromMap(Map<Object, Object> map, Class<T> clz) {
        T t = null;
        try {
            t = clz.newInstance();
            BeanMap beanMap = BeanMap.create(t);
            beanMap.putAll(map);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return t;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> List<Map<Object, Object>> toMaps(List<T> objList) {
        List<Map<Object, Object>> list = Lists.newArrayList();
        if (objList != null && !objList.isEmpty()) {
            Map<Object, Object> map;
            T bean;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = toMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> fromMaps(List<Map<Object, Object>> maps, Class<T> clazz) {
        List<T> list = Lists.newArrayList();
        if (maps != null && !maps.isEmpty()) {
            Map<Object, Object> map = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                T t = fromMap(map, clazz);
                list.add(t);
            }
        }
        return list;
    }

}
