package top.bootz.user.view.pong;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.bootz.core.base.entity.BaseEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pong extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String ack;

	private String message;

}
