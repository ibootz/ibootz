package top.bootz.security.web.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import top.bootz.security.web.controller.view.User4Regist;

/**
 * @author Zhangq<momogoing@163.com>
 * @datetime 2018年9月5日 下午11:36:48
 */

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/regist")
    public void regist(User4Regist user) {
        log.debug(user.toJson());
    }

}
