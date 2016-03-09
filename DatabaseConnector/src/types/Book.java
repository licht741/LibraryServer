package types;


import java.time.Year;
import java.util.Formatter;

public class Book extends DatabaseObject{
    String title;
    String author;
    String publishHouse;
    int year;

    public Book() {}

    public Book(String title, String author, String publishHouse, int year) {
        this.title = title;
        this.author = author;
        this.publishHouse = publishHouse;
        this.year = year;
    }

    public Book(int id, String title, String author, String publishHouse, int year) {
        super(id);
        this.title = title;
        this.author = author;
        this.publishHouse = publishHouse;
        this.year = year;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublishHouse() { return publishHouse; }
    public int getYear() { return year; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishHouse(String publishHouse) {
        this.publishHouse = publishHouse;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        formatter.format("%s - %s(%s, %s)", author, title, publishHouse, year);
        return formatter.toString();
    }
}
