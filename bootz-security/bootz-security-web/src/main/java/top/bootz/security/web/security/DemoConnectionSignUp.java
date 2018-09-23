package top.bootz.security.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import top.bootz.commons.helper.CollectionHelper;
import top.bootz.security.web.entity.Role;
import top.bootz.security.web.entity.User;
import top.bootz.security.web.service.RoleService;
import top.bootz.security.web.service.UserService;

/**
 * 实现spring social提供的钩子接口，用户采用第三方登录进入我们系统之后，如果在我们系统里找不到相关用户
 * <p>
 * 该接口会根据社交用户信息默认创建一个新用户，并将用户id返回回去，使spring social不再默认跳到账号注册/绑定页面
 * <p>
 * 这就相当于我们在后台帮助用户自己做了绑定的动作。
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月23日 上午8:53:56
 */

@Component(value = "connectionSignUp")
public class DemoConnectionSignUp implements ConnectionSignUp {

    private static final String ROLE_NORMAL = "NORMAL";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 这里根据各个业务系统的业务需求，将社交网站返回来的信息和数据库用户表数据做一个对应，保存进数据库，然后返回一个主键id
     * 
     * @param connection
     * @return
     * @author Zhangq<momogoing@163.com>
     * @datetime 2018年9月23日 上午9:24:45
     */
    @Override
    @Transactional
    public String execute(Connection<?> connection) {
        String username = connection.getDisplayName();
        User user = new User();
        user.setUsername(username);
        Role role = roleService.findByName(ROLE_NORMAL);
        user.setRoles(CollectionHelper.toSet(role));
        userService.save(user);
        return String.valueOf(user.getId());
    }

}
