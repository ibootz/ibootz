package top.bootz.security.web.service.init;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import top.bootz.security.web.entity.Authority;
import top.bootz.security.web.entity.Role;
import top.bootz.security.web.entity.User;
import top.bootz.security.web.service.AuthorityService;
import top.bootz.security.web.service.RoleService;
import top.bootz.security.web.service.UserService;

@Slf4j
@Component
@Order(value = 1)
@Transactional
public class InitData implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initAuthorities();
        initRoles();
        initUsers();
    }

    private void initUsers() {
        if (userService.count() > 0) {
            return;
        }

        log.info("开始初始化用户表 ... ");
        String[][] userDatas = { { "admin", "18550088473", "ADMIN,MANAGER" }, { "manager", "18550088474", "MANAGER" },
                { "user", "18550088475", "NORMAL" } };
        for (String[] userData : userDatas) {
            User user = userService.findByUserName(userData[0]).orElse(null);
            if (user != null) {
                continue;
            }

            user = new User();
            user.setAccountExpired(false);
            user.setAccountLocked(false);
            user.setCredentialsExpired(false);
            user.setUsername(userData[0]);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setMobile(userData[1]);
            Set<Role> roles = new HashSet<>();
            user.setRoles(roles);

            // 设置角色
            String[] roleNames = userData[2].split(",");
            for (String roleName : roleNames) {
                Role role = roleService.findByName(roleName);
                if (role == null) {
                    log.error("没有找到指定角色:[{}]，请检查该角色是否已经初始化", roleName);
                    continue;
                }
                roles.add(role);
            }
            this.userService.save(user);
        }
        log.info("用户表初始化完成!");
    }

    private void initRoles() {
        if (roleService.count() > 0) {
            return;
        }

        log.info("开始初始化角色表 ... ");
        String[][] roleDatas = { { "ADMIN",
                "ORDER_CREATE,ORDER_READ,ORDER_UPDATE,ORDER_DELETE,DEPARTMENT_DELETE,DEPARTMENT_UPDATE,DEPARTMENT_READ,DEPARTMENT_CREATE" },
                { "MANAGER", "ORDER_READ,ORDER_UPDATE,DEPARTMENT_READ,DEPARTMENT_UPDATE" },
                { "NORMAL", "ORDER_READ,DEPARTMENT_READ" } };
        for (String[] roleData : roleDatas) {
            Role role = roleService.findByName(roleData[0]);
            if (role != null) {
                continue;
            }

            role = new Role();
            role.setName(roleData[0]);
            Set<Authority> authorities = new HashSet<>();
            role.setAuthorities(authorities);

            // 设置权限
            String[] authNames = roleData[1].split(",");
            for (String authName : authNames) {
                Authority authority = authorityService.findByName(authName);
                if (authority == null) {
                    log.error("没有找到指定权限:[{}]，请检查该权限是否已经初始化", authName);
                    continue;
                }
                authorities.add(authority);
            }
            roleService.save(role);
        }
        log.info("角色表初始化完成!");
    }

    private void initAuthorities() {
        if (authorityService.count() > 0) {
            return;
        }

        log.info("开始初始化权限表 ... ");
        String[] authNames = { "ORDER_CREATE", "ORDER_READ", "ORDER_UPDATE", "ORDER_DELETE", "DEPARTMENT_CREATE",
                "DEPARTMENT_READ", "DEPARTMENT_DELETE", "DEPARTMENT_UPDATE" };
        for (String authName : authNames) {
            Authority authority = authorityService.findByName(authName);
            if (authority != null) {
                continue;
            }

            authority = new Authority();
            authority.setName(authName);
            authorityService.save(authority);
        }
        log.info("权限表初始化完成!");
    }

}
