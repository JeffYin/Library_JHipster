package com.dream.books.web.rest;

import java.io.Serializable;
import java.util.List;

import com.dream.books.domain.Item;
import com.dream.books.domain.Reader;

public class CheckoutInfoWrapper implements Serializable {
	private static final long serialVersionUID = 1710324101224159822L;
	private Reader reader; 
	private List<Item> items;
	public Reader getReader() {
		return reader;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	} 
	

}
