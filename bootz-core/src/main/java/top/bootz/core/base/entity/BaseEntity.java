package top.bootz.core.base.entity;

import java.beans.Transient;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import top.bootz.commons.helper.JsonHelper;
import top.bootz.commons.helper.ToStringHelper;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return ToStringHelper.toJSON(this, true);
	}

	@Transient
	@JsonIgnore
	public String toJson() {
		return JsonHelper.toJSON(this);
	}

}
