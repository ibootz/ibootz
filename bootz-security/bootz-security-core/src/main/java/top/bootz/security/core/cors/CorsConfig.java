package top.bootz.security.core.cors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import top.bootz.security.core.properties.SecurityProperties;
import top.bootz.security.core.properties.cors.CorsProperties;

/**
 * 跨域配置
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月24日 上午1:03:56
 */

@Configuration
public class CorsConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public CorsFilter corsFilter() {
        final CorsProperties corsProperties = securityProperties.getCors();
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        corsConfiguration.setAllowedMethods(corsProperties.getAllowedMethods());
        corsConfiguration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        if (CollectionUtils.isNotEmpty(corsProperties.getExposedHeaders())) {
            corsConfiguration.setExposedHeaders(corsProperties.getExposedHeaders());
        }
        corsConfiguration.setMaxAge(corsProperties.getMaxAge());
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
