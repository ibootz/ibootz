package top.bootz.user.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 配置主键生成器
 *
 * @author John
 * 2018年6月10日 下午10:47:07
 */

@Data
@NoArgsConstructor
@Validated
@ConfigurationProperties(prefix = "spring.application")
public class ApplicationProperties {

    @NotBlank
    private String applicationName;

}
