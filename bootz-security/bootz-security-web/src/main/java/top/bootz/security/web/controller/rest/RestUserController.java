package top.bootz.security.web.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import top.bootz.core.base.controller.BaseController;
import top.bootz.core.base.message.RestMessage;
import top.bootz.security.web.controller.view.User4Regist;
import top.bootz.security.web.entity.User;
import top.bootz.security.web.service.UserService;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年7月16日 下午11:27:57
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class RestUserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @GetMapping("/me")
    public RestMessage<UserDetails> getUserInfo(HttpServletRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return buildSuccessResponse(userDetails);
    }

    /**
     * 用户注册逻辑，或者用户与使用spring social登录之后的账号绑定逻辑
     * 
     * @param user
     * @datetime 2018年9月11日 下午9:13:10
     */
    @PostMapping("/socail/bind")
    public RestMessage<String> regist(HttpServletRequest request, User4Regist user4Regist) {
        log.debug(user4Regist.toJson());
        // 将用户填写的用户名到数据库查找到相关用户，然后将userId绑定到social使用的UserConnection表中
        User user = userService.findByUserName(user4Regist.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }

        providerSignInUtils.doPostSignUp(String.valueOf(user.getId()), new ServletRequestAttributes(request));
        return buildSuccessResponse("绑定成功");
    }

}
