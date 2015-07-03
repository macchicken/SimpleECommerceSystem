package model;

import java.util.List;

public class Products {

	private int totalPage;
	private int currentPage;
	private String keyWords;
	private List<Product> proList;

	public Products(int totalPage, int currentPage, String keyWords, List<Product> proList) {
		super();
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.keyWords=keyWords;
		this.proList = proList;
	}

	public int getTotalPage() {
		return totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public List<Product> getProList() {
		return proList;
	}
	
	
}
