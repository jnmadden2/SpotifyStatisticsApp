package db.Users;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import db.TopArtists.TopArtists;
import db.FollowedArtists.FollowedArtists;

@Entity
public class User
{
     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String spotify;
    private String display_name;
    private String picture_url;
    private String email;
    private String access_token;
    private int role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private FollowedArtists followed_artists;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TopArtists top_artists;

    /*mvn c
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */

    public User(int user_id, String spotify, String display_name, String picture_url, String email, String access_token, int role)
    {
        this.user_id = user_id;
        this.spotify = spotify;
        this.display_name = display_name;
        this.picture_url = picture_url;
        this.email = email;
        this.access_token = access_token;
        this.role = role;
        
    }

    public User()
    {

    }

    
    /** 
     * @return String
     */
    // =============================== Getters and Setters for each field ================================== //

    public String getPictureURL()
    {
        return this.picture_url;

    }
    
    /** 
     * @param picture_url
     */
    public void setPictureURL(String picture_url)
    {
        this.picture_url = picture_url;

    }
    public String getAccessToken()
    {
        return this.access_token;

    }
    public void setAccessToken(String access_token)
    {
        this.access_token = access_token;

    }
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
    public String getDisplayName()
    {
        return this.display_name;

    }
    public void setDisplayName(String display_name)
    {
        this.display_name = display_name;

    }
    public String getEmail()
    {
        return this.email;

    }
    public void setEmail(String email)
    {
        this.email = email;

    }
    public int getRole()
    {
        return this.role;

    }
    public void setRole(int role)
    {
        this.role = role;

    }
    
}
