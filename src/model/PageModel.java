package model;

import java.util.List;

public class PageModel<E> {

	private int totalPage;
	private int currentPage;
	private List<E> elementList;

	public PageModel(int totalPage, int currentPage, List<E> elementList) {
		super();
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.elementList = elementList;
	}

	public int getTotalPage() {
		return totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public List<E> getElementList() {
		return elementList;
	}
	
	
}
