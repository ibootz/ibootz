package top.bootz.user.view.user;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;
import top.bootz.core.dictionary.GenderEnum;

/**
 * 
 * @author John 2018年6月16日 上午1:36:25
 */

@Valid
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User4Add extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 登录用户名 */
    @NotBlank(message = "user.username.is_blank")
    private String username;

    /** 加密之后的密码(加密算法：BCrypt) */
    @NotBlank(message = "user.password.is_blank")
    private String password;

    /** 员工姓名 */
    @NotBlank(message = "user.realName.is_blank")
    private String realName;

    /** 性别 */
    @Pattern(regexp = "[mfou]", message = "user.gender.is_invalid")
    private GenderEnum gender;

    /** 电话 */
    private String mobile;

    /** 身份证 */
    private String idCard;

    /** 邮箱 */
    private String email;

}
