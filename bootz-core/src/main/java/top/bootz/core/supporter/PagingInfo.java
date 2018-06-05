package top.bootz.core.supporter;

import java.io.Serializable;
import java.util.List;

public class PagingInfo implements Serializable {

	private static final long serialVersionUID = 2724475080681352450L;

	public static final int MAX_PAGESIZE = 1000;

	public static final int DEFAULT_PAGESIZE = 50;

	private Integer pageSize; // 每页数据条数

	private Integer offset; // 当前页数据的偏移量

	private Integer totalPages; // 按照当前limit数值计算出的总页数

	private Integer totalCount; // 总记录数

	private List<SortInfo> sorts; // 排序字段，支持多字段按照优先级排序

	public PagingInfo() {
	}

	public PagingInfo(Integer pageSize, Integer offset, List<SortInfo> sorts) {
		super();
		this.pageSize = pageSize;
		this.offset = offset;
		this.setSorts(sorts);
	}

	public Integer getPageSize() {
		if (pageSize == null || pageSize <= 0) {
			this.pageSize = PagingInfo.DEFAULT_PAGESIZE;
		} else if (this.pageSize > PagingInfo.MAX_PAGESIZE) {
			this.pageSize = PagingInfo.MAX_PAGESIZE;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOffset() {
		if (offset == null || offset < 0) {
			this.offset = 0;
		} else if (totalCount != null && offset > totalCount) {
			this.offset = totalCount;
		}
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCurPage() {
		return getOffset() / getPageSize();
	}

	public List<SortInfo> getSorts() {
		return sorts;
	}

	public void setSorts(List<SortInfo> sorts) {
		this.sorts = sorts;
	}

}
