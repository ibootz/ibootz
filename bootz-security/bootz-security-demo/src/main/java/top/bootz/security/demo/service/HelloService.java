/**
 * 
 */
package top.bootz.security.demo.service;

import org.springframework.stereotype.Service;

/**
 * @author zhailiang
 *
 */
@Service
public class HelloService {

	public String greeting(String name) {
		System.out.println("greeting");
		return "hello " + name;
	}

}
