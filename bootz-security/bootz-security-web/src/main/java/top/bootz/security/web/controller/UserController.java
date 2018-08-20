package top.bootz.security.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import top.bootz.core.base.controller.BaseController;
import top.bootz.core.base.message.RestMessage;

/**
 * @Author : Zhangq <momogoing@163.com>
 * @CreationDate : 2018年7月16日 下午11:27:57
 */

@Slf4j
@RestController
public class UserController extends BaseController {

	@GetMapping("/user/me")
	public RestMessage<UsernamePasswordAuthenticationToken> user(HttpServletRequest request, Principal principal) {
		UsernamePasswordAuthenticationToken authenticationToken = null;
		if (principal instanceof UsernamePasswordAuthenticationToken) {
			authenticationToken = (UsernamePasswordAuthenticationToken) principal;
		}
		return buildSuccessResponse(authenticationToken);
	}

}
