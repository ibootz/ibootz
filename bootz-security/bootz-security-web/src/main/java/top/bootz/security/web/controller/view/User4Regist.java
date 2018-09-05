package top.bootz.security.web.controller.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.core.base.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
public class User4Regist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

}
