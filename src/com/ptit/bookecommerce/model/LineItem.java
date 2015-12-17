package com.ptit.bookecommerce.model;

public class LineItem {
	private Book book;
	private int quantity;

	public LineItem() {
		super();
	}

	public LineItem(Book book, int quantity) {
		super();
		this.book = book;
		this.quantity = quantity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
