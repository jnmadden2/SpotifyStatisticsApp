package db.SearchedAlbums;

import javax.persistence.*;

@Entity
public class SearchedAlbums {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String spotify;

    @Lob
    @Column
    private String items;

    // Constructors
    public SearchedAlbums()
    {

    }

    public SearchedAlbums(int id, String spotify, String items) {
        this.id = id;
        this.spotify = spotify;
        this.items = items;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
