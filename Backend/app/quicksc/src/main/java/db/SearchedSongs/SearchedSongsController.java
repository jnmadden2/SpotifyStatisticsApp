package db.SearchedSongs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchedSongsController {
    @Autowired
    private final SearchedSongsRepository searchedSongsRepository;

    public SearchedSongsController(SearchedSongsRepository searchedSongsRepository) {
        this.searchedSongsRepository = searchedSongsRepository;
    }

    @GetMapping(path = "/users/{spotify}/searchedsongs")
    SearchedSongs getUserSearchedSongs(@PathVariable String spotify) {
        return this.searchedSongsRepository.findBySpotify(spotify);
    }

    @PostMapping(path = "/users/update/{spotify}/searchedsongs")
    void updateUserSearchedSongs(@PathVariable String spotify, @RequestBody SearchedSongs updatedUser) {
        this.searchedSongsRepository.deleteBySpotify(spotify);
        this.searchedSongsRepository.save(updatedUser);
    }
}
