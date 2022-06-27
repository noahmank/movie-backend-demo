package application.controllers;

import application.models.Movie;
import application.MovieNotFoundException;
import application.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieApiController {
    @Autowired
    private final MovieRepository repository;

    MovieApiController(MovieRepository repository) {
        this.repository = repository;
    }

    /**
     * Get all movies
     * @return List of all movies
     */
    @GetMapping("/movies")
    List<Movie> all() {
        return repository.findAll();
    }

    /**
     * Get one movie by id
     * @param id of the movie
     * @return single movie by id
     */
    @GetMapping("/movies/id={id}")
    Movie oneById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    /**
     * Get a list of movies containing string, ignoring case
     * @param title string to compare to title
     * @return list of movies that contain title
     */
    @GetMapping("/movies/{title}")
    List<Movie> searchByTitle(@PathVariable String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Create and persist a new movie
     * @param movie
     * @return
     */
    @PostMapping("/movies")
    Movie newMovie(@RequestBody Movie movie) {
        return repository.save(movie);
    }

    /**
     * Update movie info
     * @param updatedMovie content representing new movie object
     * @param id of movie to modify
     * @return the persisted movie object
     */
    @PutMapping("/movies/{id}")
    Movie updateMovie(@RequestBody Movie updatedMovie, @PathVariable Long id) {
        return repository.findById(id).map(movie -> {
            movie.setTitle(updatedMovie.getTitle());
            movie.setDescription(updatedMovie.getDescription());
            movie.setDuration(updatedMovie.getDuration());
            movie.setRating(updatedMovie.getRating());
            movie.setReleaseYear(updatedMovie.getReleaseYear());
            return repository.save(movie);
        }).orElseGet(() -> {
            updatedMovie.setId(id);
            return repository.save(updatedMovie);
        });
    }

    /**
     * Delete movie by id
     * @param id of movie to delete
     */
    @DeleteMapping("/movies/id={id}")
    void deleteMovie(@PathVariable Long id) {
        repository.deleteById(id);
    }

    /**
     * Delete all movies
     */
    @DeleteMapping("/movies")
    void deleteAllMovies() {
        repository.deleteAll();
    }

    /**
     * Add an anonymous like to a movie
     * @param id of movie to like
     * @return updated movie object
     */
    @PutMapping("/movies/like/{id}")
    Movie likeMovie(@PathVariable Long id) {
        Movie movie = repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        movie.setLikes(movie.getLikes() + 1);
        return repository.save(movie);
    }

    /**
     * Remove an anonymous like from a movie
     * @param id of movie to unlike
     * @return updated movie object
     */
    @PutMapping("/movies/undo_like/{id}")
    Movie undoLikeMovie(@PathVariable Long id) {
        Movie movie = repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        movie.setLikes(movie.getLikes() - 1);
        return repository.save(movie);
    }

    /**
     * Add an anonymous dislike to a movie
     * @param id of movie to dislike
     * @return updated movie object
     */
    @PutMapping("/movies/dislike/{id}")
    Movie dislikeMovie(@PathVariable Long id) {
        Movie movie = repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        movie.setDislikes(movie.getDislikes() + 1);
        return repository.save(movie);
    }

    /**
     * Remove an anonymous dislike from a movie
     * @param id of movie to undislike
     * @return updated movie object
     */
    @PutMapping("/movies/undo_dislike/{id}")
    Movie undoDislikeMovie(@PathVariable Long id) {
        Movie movie = repository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        movie.setDislikes(movie.getDislikes() - 1);
        return repository.save(movie);
    }
}
