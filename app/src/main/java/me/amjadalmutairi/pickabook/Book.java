package me.amjadalmutairi.pickabook;

import java.util.ArrayList;

/**
 * Created by amjadalmutairi on 7/26/17.
 */

class Book {
    private String title;
    private ArrayList<String> authors;
    private String publishedDate;
    private String description;
    private int pageCount;

    Book(String title, ArrayList<String> authors, String publishedDate, String description, int pageCount) {
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
    }

    String getTitle() {
        return title;
    }

    ArrayList<String> getAuthors() {
        return authors;
    }

    String getPublishedDate() {
        return publishedDate;
    }

    String getDescription() {
        return description;
    }

    int getPageCount() {
        return pageCount;
    }
}
