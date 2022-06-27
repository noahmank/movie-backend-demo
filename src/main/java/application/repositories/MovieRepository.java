package application.repositories;

import application.models.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    List<Movie> findAll();
    List<Movie> findByTitleContainingIgnoreCase(String title);
}
