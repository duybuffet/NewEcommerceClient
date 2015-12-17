package com.ptit.bookecommerce.model;

public class Ebook {
	private int id;
	private String description;
	private String author;
	private String update;
	private String name;
	private String cover;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Ebook(int id, String description, String author, String update,
			String name, String cover) {
		super();
		this.id = id;
		this.description = description;
		this.author = author;
		this.update = update;
		this.name = name;
		this.cover = cover;
	}

	public Ebook() {
		super();
	}

}
