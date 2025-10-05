package db.MinutesListened;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import db.Users.User;;

@Entity
public class MinutesListened
{
     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String spotify;

    @Lob
    @Column
    private String items;

    @OneToOne
    private User user;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */

    public MinutesListened(int user_id, String spotify, String items)
    {
        this.user_id = user_id;
        this.spotify = spotify;
        this.items = items;
        
    }

    public MinutesListened()
    {

    }

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
