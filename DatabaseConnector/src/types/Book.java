package types;


import java.time.Year;
import java.util.Formatter;

public class Book extends DatabaseObject{
    String title;
    String author;
    String publishHouse;
    Year year;

    public Book() {}

    public Book(int id, String title, String author, String publishHouse, Year year) {
        super(id);
        this.title = title;
        this.author = author;
        this.publishHouse = publishHouse;
        this.year = year;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublishHouse() { return publishHouse; }
    public Year getYear() { return year; }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        formatter.format("%s - %s(%s, %s)", author, title, publishHouse, year);
        return formatter.toString();
    }
}
