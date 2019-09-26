package br.pucminas.library;

import java.util.List;

public class Pagination<T extends Object> {
	private List<T> data;

	private Integer limit;

	private Integer offset;

	private Integer total;

	private Integer count;

	public Pagination() {
	}

	public Pagination(List<T> data, Integer limit, Integer offset) {
		this.data = data;
		this.limit = limit;
		this.offset = offset;
		this.total = data.size();
		this.count = data == null ? 0 : data.size();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
