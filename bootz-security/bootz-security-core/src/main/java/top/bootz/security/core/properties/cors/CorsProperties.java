package top.bootz.security.core.properties.cors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Data;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月24日 上午1:04:59
 */

@Data
public class CorsProperties {

    public static final String ALL = "*";

    private boolean allowCredentials = true;

    private List<String> allowedOrigins = Collections.unmodifiableList(Arrays.asList(ALL));

    private List<String> allowedHeaders = Collections.unmodifiableList(Arrays.asList(ALL));

    private List<String> allowedMethods = Collections.unmodifiableList(Arrays.asList(ALL));

    private List<String> exposedHeaders;

    private long maxAge = 3600L;

}
