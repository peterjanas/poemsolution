package dk.cphbusiness.dtos;

import dk.cphbusiness.persistence.entities.Poem;
import lombok.Getter;
import lombok.Setter;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
@Getter
@Setter
public class PoemDTO {
    private long id;
    private String title;
    private String author;
    private String text;

    public PoemDTO(long id, String title, String author, String text) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.text = text;
    }

    public PoemDTO(String title, String author, String text) {
        this.title = title;
        this.author = author;
        this.text = text;
    }
    public PoemDTO(Poem poem) {
        this.id = poem.getId();
        this.title = poem.getTitle();
        this.author = poem.getAuthor();
        this.text = poem.getText();
    }
    public Poem toEntity(){
        return new Poem(this.id, this.title, this.author, this.text);
    }
    public PoemDTO() {
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
        return "PoemDTO{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
