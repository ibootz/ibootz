package top.bootz.core.base.entity;

import java.beans.Transient;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import top.bootz.commons.helper.JsonHelper;
import top.bootz.commons.helper.ToStringHelper;

public class BaseCriteria implements Serializable {

	private static final long serialVersionUID = 8591142014745925855L;

	@Transient
	@JsonIgnore
	@Override
	public String toString() {
		return ToStringHelper.toJSON(this);
	}

	@Transient
	@JsonIgnore
	public String toJson() {
		return JsonHelper.toJSON(this);
	}

}
