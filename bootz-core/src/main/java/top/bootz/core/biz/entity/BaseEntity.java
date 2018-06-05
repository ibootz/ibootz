package top.bootz.core.biz.entity;

import java.beans.Transient;
import java.io.Serializable;

import net.minidev.json.annotate.JsonIgnore;
import top.bootz.commons.helper.JsonHelper;
import top.bootz.commons.helper.SerializableHelper;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -3685750813388436101L;

	@Override
	public String toString() {
		return SerializableHelper.toJSON(this);
	}

	@Transient
	@JsonIgnore
	public String toJson() {
		return JsonHelper.toJSON(this);
	}

}
