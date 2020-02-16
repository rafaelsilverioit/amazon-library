package br.pucminas.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
	@Singular
	private List<T> items;

	private Integer limit;

	private Integer offset;

	private Integer total;

	private Integer count;

	public Pagination(List<T> items, Integer limit, Integer offset) {
		this.items = items;
		this.limit = limit;
		this.offset = offset;

		int count = items == null ? 0 : items.size();
		this.total = count;
		this.count = count;
	}
}
