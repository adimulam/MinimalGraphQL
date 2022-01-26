package com.example.dw.model;

import java.util.ArrayList;
import java.util.List;

public class Books
{
    private List<Book> bookList;

    public List<Book> getBookList() {
        if(bookList == null) {
            bookList = new ArrayList<>();
        }
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Book getBook(int id) {
        return bookList.get(id);
    }
}
