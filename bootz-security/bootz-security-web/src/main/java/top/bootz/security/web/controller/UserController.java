package top.bootz.security.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.bootz.core.base.controller.BaseController;
import top.bootz.core.base.message.RestMessage;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年7月16日 下午11:27:57
 */

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @GetMapping("/me")
    public RestMessage<UserDetails> user(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        return buildSuccessResponse(userDetails);
    }

}
