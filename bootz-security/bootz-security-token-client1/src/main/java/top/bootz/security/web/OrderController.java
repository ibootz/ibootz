package top.bootz.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OAuth2RestOperations auth2RestOperations;

    @GetMapping("/user")
    public Order toOrderUser() {
        return auth2RestOperations.getForObject("http://resource1.bootz.top:5083/order/user", Order.class);
    }

    @GetMapping("/manager")
    public Order toOrderManager() {
        return auth2RestOperations.getForObject("http://resource1.bootz.top:5083/order/manager", Order.class);
    }

    @GetMapping("/admin")
    public Order toOrderAdmin() {
        return auth2RestOperations.getForObject("http://resource1.bootz.top:5083/order/admin", Order.class);
    }

}
