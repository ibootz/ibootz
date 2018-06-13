package top.bootz.core.supporter;

import java.util.ArrayList;
import java.util.List;

public class PagingSupport<T> {

	private List<T> results = new ArrayList<>();

	private PagingInfo pagingInfo;

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

}
