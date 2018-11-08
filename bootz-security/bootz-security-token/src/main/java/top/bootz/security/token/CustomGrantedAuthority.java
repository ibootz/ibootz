package top.bootz.security.token;

import org.springframework.security.core.GrantedAuthority;

/**
 * 自定义spring security的Authority = 我们自己系统中自定义的role 和 menu
 * 
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年11月8日 下午11:49:55
 */
public class CustomGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String roleId; // 这里可以是id，也可是是code

    private String menuId; // 这里可以是id，也可是是code

    @Override
    public String getAuthority() {
        return roleId + "&" + menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public CustomGrantedAuthority(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
