package com.ptit.bookecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<LineItem> listItems;

	public Cart() {
		super();
		listItems = new ArrayList<>();
	}

	public List<LineItem> getListItems() {
		return listItems;
	}

	public void setListItems(List<LineItem> listItems) {
		this.listItems = listItems;
	}

	public void addToCart(Book b) {
		int bookId = b.getId();
		boolean flag = false;
		for (LineItem item : listItems) {
			if (item.getBook().getId() == bookId) {
				item.setQuantity(item.getQuantity() + 1);
				flag = true;
				break;
			}
		}

		if (flag == false) {
			listItems.add(new LineItem(b, 1));
		}
	}

	public void updateCart(int bookId, int qtt) {
		for (LineItem item : listItems) {
			if (item.getBook().getId() == bookId) {
				if (qtt == 0) {
					listItems.remove(item);
				} else {
					item.setQuantity(qtt);
				}
				break;
			}
		}
	}

	public void delete(Book b) {
		for (LineItem item : listItems) {
			if (item.getBook().getId() == b.getId()) {
				listItems.remove(item);
				break;
			}
		}
	}

	public void deleteAll() {
		listItems = new ArrayList<>();
	}

	public Double getTotal() {
		Double res = 0.0;
		for (LineItem item : listItems) {
			res += (item.getBook().getPrice() * item.getQuantity() - item
					.getBook().getPrice()
					* (item.getBook().getDiscount() / 100.0)
					* item.getQuantity());
		}
		return res;
	}
}
