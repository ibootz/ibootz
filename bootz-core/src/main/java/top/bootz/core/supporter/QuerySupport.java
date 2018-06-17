package top.bootz.core.supporter;

import top.bootz.core.base.entity.BaseEntity;

public class QuerySupport<T> extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private T query;

	private PagingInfo paging;

	public QuerySupport() {
	}

	public QuerySupport(PagingInfo paging, T query) {
		super();
		this.paging = paging;
		this.query = query;
	}

	public PagingInfo getPaging() {
		return paging;
	}

	public void setPaging(PagingInfo paging) {
		this.paging = paging;
	}

	public T getQuery() {
		return query;
	}

	public void setQuery(T query) {
		this.query = query;
	}

}
