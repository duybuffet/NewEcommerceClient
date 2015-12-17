package com.ptit.bookecommerce.model;

public class Book {
	private int id;
	private String isbn;
	private String title;
	private String description;
	private String coverUrl;
	private float price;
	private int discount;
	private int stock;
	private int numPages;

	public Book() {
		super();
	}

	public Book(int id, String isbn, String title, String description,
			String coverUrl, float price, int discount, int stock, int numPages) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.description = description;
		this.coverUrl = coverUrl;
		this.price = price;
		this.discount = discount;
		this.stock = stock;
		this.numPages = numPages;
	}

	public Book(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getNumPages() {
		return numPages;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

}
