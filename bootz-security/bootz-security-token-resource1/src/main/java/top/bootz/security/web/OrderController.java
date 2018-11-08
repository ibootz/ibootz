package top.bootz.security.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Order getOrderByAdmin() {
		return new Order("Admin", 8.50);
	}

	@GetMapping("/everyOne")
	public Order getOrderByEveryone() {
		return new Order("EveryOne", 12.00);
	}

	@GetMapping("/manager")
	@PreAuthorize("hasAuthority('MANAGER')")
	public Order getOrderByManager() {
		return new Order("Manager", 45.20);
	}

}
