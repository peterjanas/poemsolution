package dk.cphbusiness.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
@Entity
public class Poem implements IJPAEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String author;
    private String text;

    public Poem(String title, String author, String text) {
        this.title = title;
        this.author = author;
        this.text = text;
    }

    public Poem(long id, String title, String author, String text) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.text = text;
    }
    public Poem() {
    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Poem{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
