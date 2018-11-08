package top.bootz.security.session.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import top.bootz.core.base.controller.BaseController;
import top.bootz.core.base.message.RestMessage;
import top.bootz.security.core.SecurityConstants;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年8月19日 下午3:27:37
 */

@RestController
public class SessionSecurityController extends BaseController {

    @GetMapping(SecurityConstants.DEFAULT_SESSION_INVALID_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestMessage<String> sessionInvalid(HttpServletRequest request, HttpServletResponse response) {
        return buildSimpleErrorResponse("session失效，请重新登录！");
    }

}
