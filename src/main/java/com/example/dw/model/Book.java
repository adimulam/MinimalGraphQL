package com.example.dw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Book implements Serializable {

    public Book(Integer id, String countryOfOrigin, String publisher, String contact, int pages) {
        super();
        this.id = id;
        this.countryOfOrigin = countryOfOrigin;
        this.publisher = publisher;
        this.contact = contact;
        this.pages = pages;
    }

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("countryOfOrigin")
    private String countryOfOrigin;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("pages")
    private int pages;

    @Override
    public String toString() {
        return "Book [id=" + id + ", countryOfOrigin=" + countryOfOrigin + ", publisher=" + publisher + "]";
    }
}
