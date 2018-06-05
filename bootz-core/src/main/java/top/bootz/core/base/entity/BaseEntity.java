package top.bootz.core.base.entity;

import java.beans.Transient;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import top.bootz.commons.helper.JsonHelper;
import top.bootz.commons.helper.SerializableHelper;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 3426943173573046791L;

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
