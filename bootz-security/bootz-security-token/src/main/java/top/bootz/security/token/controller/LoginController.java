package top.bootz.security.token.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices tokenServices;

    // 获取认证人的相关信息
    @GetMapping("/userInfo")
    @ResponseBody
    public Authentication getUserInfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // 登出操作，也就是废弃该AccessToken，使其无效
    @GetMapping("/logout")
    public ModelAndView logout(String accessToken) {
        tokenServices.revokeToken(accessToken);
        return new ModelAndView("ftl/login");
    }

}
