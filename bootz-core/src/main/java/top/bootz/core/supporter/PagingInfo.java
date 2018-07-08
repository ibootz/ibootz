package top.bootz.core.supporter;

import java.io.Serializable;
import java.util.List;

public class PagingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int MAX_PAGESIZE = 1000;

    public static final int DEFAULT_PAGESIZE = 20;

    private int pageSize; // 每页数据条数

    private int offset; // 当前页数据的偏移量

    private int totalPages; // 按照当前limit数值计算出的总页数

    private int totalCount; // 总记录数

    private List<SortInfo> sorts; // 排序字段，支持多字段按照优先级排序

    public PagingInfo() {
    }

    public PagingInfo(int pageSize, int offset, List<SortInfo> sorts) {
        super();
        this.pageSize = pageSize;
        this.offset = offset;
        this.setSorts(sorts);
    }

    public int getPageSize() {
        if (pageSize <= 0) {
            this.pageSize = PagingInfo.DEFAULT_PAGESIZE;
        } else if (this.pageSize > PagingInfo.MAX_PAGESIZE) {
            this.pageSize = PagingInfo.MAX_PAGESIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        if (offset < 0) {
            this.offset = 0;
        } else if (offset > totalCount) {
            this.offset = totalCount;
        }
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurPage() {
        return getOffset() / getPageSize();
    }

    public List<SortInfo> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortInfo> sorts) {
        this.sorts = sorts;
    }

}
