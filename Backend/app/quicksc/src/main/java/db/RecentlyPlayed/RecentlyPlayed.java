package db.RecentlyPlayed;

import javax.persistence.*;
import db.Users.User;

@Entity
public class RecentlyPlayed
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String spotify;


    @Lob
    @Column
    private String items;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public RecentlyPlayed(int user_id, String spotify, String items, User user)
    {
        this.user_id = user_id;
        this.spotify = spotify;
        this.items = items;
        this.user = user;
    }

    public RecentlyPlayed()
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
    public User getUser()
    {
        return this.user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }
}
