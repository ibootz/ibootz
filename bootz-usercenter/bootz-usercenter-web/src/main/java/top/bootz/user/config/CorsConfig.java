package top.bootz.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import top.bootz.user.properties.CorsConfigProperties;

/**
 * 跨域配置
 */

@Configuration
public class CorsConfig {

    @Autowired
    private CorsConfigProperties corsConfigProperties;

    @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(corsConfigProperties.getAllowCredentials());
        corsConfiguration.addAllowedOrigin(corsConfigProperties.getAllowedOrigin());
        corsConfiguration.addAllowedHeader(corsConfigProperties.getAllowedHeader());
        corsConfiguration.addAllowedMethod(corsConfigProperties.getAllowedMethod());
        corsConfiguration.setExposedHeaders(corsConfigProperties.getExposedHeaders());
        corsConfiguration.setMaxAge(corsConfigProperties.getMaxAge());
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(urlBasedCorsConfigurationSource));
        bean.setOrder(0);
        return bean;

    }

}

