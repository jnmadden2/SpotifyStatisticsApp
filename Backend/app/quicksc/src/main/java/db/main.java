package db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;

import db.Users.User;
import db.Users.UserController;
import db.Users.UserRepository;
import db.Websocket.ChatServer;
import db.Websocket.WebSocketConfig;
import db.Websocket.DirectMessageServer;
import db.TopSongs.TopSongs;
import db.TopSongs.TopSongsController;
import db.TopSongs.TopSongsRepository;
import db.RecentlyPlayed.RecentlyPlayed;
import db.RecentlyPlayed.RecentlyPlayedController;
import db.RecentlyPlayed.RecentlyPlayedRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"db", "db.Users", "db.Websocket", "db.TopSongs", "db.RecentlyPlayed", "db.TopArtists", "db.FollowedArtists", "db.MinutesListened"})
@EnableJpaRepositories
class Main
{
    
    /** 
     * @param args
     */
    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);

    }

}
