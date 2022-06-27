package application.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public String toString() {
        return "application.models.Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", duration=" + duration +
                ", rating=" + rating +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }

    private String title;
    private String description;
    private int releaseYear;
    private int duration; // in minutes
    private int rating; // out of 10
    private int likes;
    private int dislikes;

    public Movie() {

    }

    public Movie(String title, String description, int releaseYear, int duration, int rating) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.likes = 0;
        this.dislikes = 0;
    }

    public Movie(Long id, String title, String description, int releaseYear, int duration, int rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.likes = 0;
        this.dislikes = 0;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return releaseYear == movie.releaseYear && duration == movie.duration && rating == movie.rating && likes == movie.likes && dislikes == movie.dislikes && id.equals(movie.id) && title.equals(movie.title) && description.equals(movie.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, releaseYear, duration, rating, likes, dislikes);
    }
}
