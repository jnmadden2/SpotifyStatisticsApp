package db.TopSongs;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class TopSongs 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String spotify;

    @Lob
    @Column
    private String items;

    public  TopSongs(int user_id, String spotify, String items) 
    {
        this.user_id = user_id;
        this.spotify = spotify;
        this.items = items;

    }

    public TopSongs() 
    {

    }

    
    /** 
     * @return int
     */
    // =============================== Getters and Setters for each field ================================== //

    public int getID()
    {
        return this.user_id;

    }
    public void setID(int user_id)
    {
        this.user_id = user_id;

    }
    public String getSpotify()
    {
        return this.spotify;

    }
    public void setSpotify(String spotify)
    {
        this.spotify = spotify;

    }
    public String getItems ()
    {
        return this.items;

    }
    public void setItems (String items)
    {
        this.items = items;

    }

}
