package top.bootz.user.entity.mysql.resource;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.CommonEntity;
import top.bootz.core.converter.attribute.DisableTypeAttributeConverter;
import top.bootz.core.dictionary.DisableTypeEnum;

/**
 * Spring Security 中该用户可以访问的url前缀，相当于一种资源的控制
 * 
 * @author John
 *
 */
@Entity
@Table(name = "uc_url")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Url extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /** 请求路径前缀 */
    private String urlExpression;

    /** 是否处于不可用状态 */
    private DisableTypeEnum disable;

    @Column(name = "url_expression", nullable = false, columnDefinition = "varchar(32) default '' comment '请求路径前缀'")
    public String getUrlExpression() {
        return urlExpression;
    }

    @Convert(converter = DisableTypeAttributeConverter.class)
    @Column(name = "disable", nullable = false, columnDefinition = "tinyint(1) default 0 comment '是否处于不可用状态（0-可用，1-不可用）'")
    public DisableTypeEnum getDisable() {
        return this.disable == null ? DisableTypeEnum.ENABLE : this.disable;
    }

}
