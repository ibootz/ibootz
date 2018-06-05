package top.bootz.core.supporter;

public class SortInfo {

	private String sortFiled;

	private String direction;

	private Integer priority; // 排序优先级

	public SortInfo() {
		super();
	}

	public SortInfo(String sortFiled, String direction, Integer priority) {
		super();
		this.sortFiled = sortFiled;
		this.direction = direction;
		this.priority = priority;
	}

	public String getSortFiled() {
		return sortFiled;
	}

	public void setSortFiled(String sortFiled) {
		this.sortFiled = sortFiled;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
