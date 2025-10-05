package db.SearchedAlbums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchedAlbumsController {
    @Autowired
    private final SearchedAlbumsRepository searchedAlbumsRepository;

    public SearchedAlbumsController(SearchedAlbumsRepository searchedAlbumsRepository) {
        this.searchedAlbumsRepository = searchedAlbumsRepository;
    }

    @GetMapping(path = "/users/{spotify}/searchedalbums")
    SearchedAlbums getUserSearchedAlbums(@PathVariable String spotify) {
        return this.searchedAlbumsRepository.findBySpotify(spotify);
    }

    @PostMapping(path = "/users/update/{spotify}/searchedalbums")
    void updateUserSearchedAlbums(@PathVariable String spotify, @RequestBody SearchedAlbums updatedUser) {
        this.searchedAlbumsRepository.deleteBySpotify(spotify);
        this.searchedAlbumsRepository.save(updatedUser);
    }
}
