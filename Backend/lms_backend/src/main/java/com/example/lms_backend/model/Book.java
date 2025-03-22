package com.example.lms_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
	private Long book_id;
	private String title;
	private String author;
	private String isbn;
	private int qty;
	private int branch_id;
	private int avilable;
	
	public Book()
	{
	}
	
	public Book ( long id,  String title, String author, String isbn, int qty, int branch, int available)
	{
		this.book_id = id;
		
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.qty = qty;
		this.branch_id = branch;
		this.avilable = available;
	}

	public long getBook_id() {
		return book_id;
	}

	public void setBook_id(long book_id) {
		this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getISBN() {
		return isbn;
	}

	public void setISBN(String iSBN) {
		this.isbn = iSBN;
	}

	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public int getBranch_id() {
		return branch_id;
	}
	
	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}
	
	public int getAvilable() {
		return avilable;
	}
	
	public void setAvilable(int avilable) {
		this.avilable = avilable;
	}
	
}
