package top.bootz.security.web;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private int age;

}
