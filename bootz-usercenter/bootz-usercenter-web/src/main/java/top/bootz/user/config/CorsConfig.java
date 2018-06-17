package top.bootz.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import top.bootz.user.config.properties.CorsConfigProperties;

/**
 * 跨域支持
 * 
 * @author John <br/>
 *         2018年6月16日 上午1:25:05
 */
@Configuration
public class CorsConfig {

	@Autowired
	private CorsConfigProperties corsConfigProperties;

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(corsConfigProperties.getAllowCredentials());
		corsConfiguration.setAllowedOrigins(corsConfigProperties.getAllowedOrigin());
		corsConfiguration.addAllowedHeader(corsConfigProperties.getAllowedHeader());
		corsConfiguration.setAllowedMethods(corsConfigProperties.getAllowedMethod());
		corsConfiguration.setMaxAge(corsConfigProperties.getMaxAge());
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
				new CorsFilter(urlBasedCorsConfigurationSource));
		bean.setOrder(0);
		return bean;
	}

}
