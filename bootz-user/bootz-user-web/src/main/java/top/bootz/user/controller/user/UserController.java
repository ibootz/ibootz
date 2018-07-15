package top.bootz.user.controller.user;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.bootz.commons.helper.BeanHelper;
import top.bootz.commons.helper.RandomHelper;
import top.bootz.commons.helper.RsaHelper;
import top.bootz.core.base.dto.RestMessage;
import top.bootz.core.dictionary.GenderEnum;
import top.bootz.core.dictionary.MessageStatusEnum;
import top.bootz.user.controller.BaseController;
import top.bootz.user.entity.mysql.user.User;
import top.bootz.user.service.mysql.UserService;
import top.bootz.user.view.user.User4Add;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestMessage> add(HttpServletRequest request, @RequestBody User4Add user4Add)
            throws Exception {
        User user = new User();
        BeanHelper.copyProperties(user4Add, user);

        if (StringUtils.isBlank(user4Add.getUsername())) {
            user.setUsername(RandomHelper.randomString(9));
        }
        user.setRealName(RandomHelper.randomChineseName());
        user.setEmail(RandomHelper.randomEmail());
        user.setIdCard(RandomHelper.randomIdCard());
        user.setMobile(RandomHelper.randomMobile());
        Optional<String> opt = RsaHelper.encryptToBase64(RandomHelper.randomString(8, 18).getBytes());
        if (opt.isPresent()) {
            user.setPassword(opt.get());
        }
        user.setGender(GenderEnum.getGenderByCode(RandomHelper.randomString(1, "mfou")));
        userService.asyncSave(user);

        return buildRestMessage(HttpStatus.CREATED, MessageStatusEnum.SUCCESS, buildLocation(request, user.getId()),
                null);
    }

}
