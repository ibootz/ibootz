package top.bootz.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private OAuth2RestOperations auth2RestOperations;

    @GetMapping("/user")
    public String toOrderPage() {
        return auth2RestOperations.getForObject("http://resource2.bootz.top:5084/user/me", String.class);
    }

}
