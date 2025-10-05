package db.SearchedArtists;

import db.SearchedArtists.SearchedArtists;
import db.SearchedArtists.SearchedArtistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchedArtistsController {
    @Autowired
    private final SearchedArtistsRepository searchedArtistsRepository;

    public SearchedArtistsController(SearchedArtistsRepository searchedArtistsRepository) {
        this.searchedArtistsRepository = searchedArtistsRepository;
    }

    @GetMapping(path = "/users/{spotify}/searchedartists")
    SearchedArtists getUserSearchedArtists(@PathVariable String spotify) {
        return this.searchedArtistsRepository.findBySpotify(spotify);
    }

    @PostMapping(path = "/users/update/{spotify}/searchedartists")
    void updateUserSearchedArtists(@PathVariable String spotify, @RequestBody SearchedArtists updatedUser) {
        this.searchedArtistsRepository.deleteBySpotify(spotify);
        this.searchedArtistsRepository.save(updatedUser);
    }
}
