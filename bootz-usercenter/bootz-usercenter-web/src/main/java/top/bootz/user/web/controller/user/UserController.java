package top.bootz.user.web.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import top.bootz.commons.helper.BeanHelper;
import top.bootz.commons.helper.SpringHelper;
import top.bootz.commons.snowflake.Snowflake;
import top.bootz.user.entity.mysql.user.User;
import top.bootz.user.service.mysql.UserService;
import top.bootz.user.web.controller.BaseController;
import top.bootz.user.web.view.user.User4Add;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private Snowflake snowflake;

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void add(HttpServletRequest request, @RequestBody User4Add user4Add) {
		User user = new User();
		BeanHelper.copyProperties(user4Add, user);
		userService.saveUser(user);

		log.debug("snowflake [" + snowflake + "]");
		log.debug("springHelper [{}]", (SpringHelper.getBean("useService") == userService));
	}

}
