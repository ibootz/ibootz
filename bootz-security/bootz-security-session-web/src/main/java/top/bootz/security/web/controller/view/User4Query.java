package top.bootz.security.web.controller.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.bootz.core.base.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = false)
public class User4Query extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String username;

    private int age;

    private int ageTo;

}
