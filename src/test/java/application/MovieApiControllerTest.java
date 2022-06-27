package application;

import application.models.Movie;
import application.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository repository;

    @Test
    public void contextLoads() {
        assertNotNull(this.repository);
        assertNotNull(this.mockMvc);
    }

    @BeforeEach
    public void setup() throws Exception {
        this.repository.save(new Movie(1L, "Paddington", "Best movie", 2014, 95, 10));
        this.repository.save(new Movie(2L, "Star Wars IV", "Great Movie", 1977, 121, 9));
    }

    @Test
    public void testCreateNewMovie() throws Exception {
        this.mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"title\": \"The Dark Knight Rises\", \"description\": \"Good movie\", \"releaseYear\": 2012, " +
                        "\"duration\": 165, \"rating\": 8}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title").value("The Dark Knight Rises"))
                .andExpect(jsonPath("$.description").value("Good movie"));
    }

    @Test
    public void testGetMovieById() throws Exception {
        this.mockMvc.perform(get("/movies/id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Paddington"));
    }

    @Test
    public void testGetAllMovies() throws Exception {
        this.mockMvc.perform(get("/movies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testGetMoviesByName() throws Exception {
        this.mockMvc.perform(get("/movies/padd")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Paddington"));
    }

    @Test
    public void testLikes() throws Exception {
        this.mockMvc.perform(put("/movies/like/1"))
                .andExpect(status().isOk())
                .andDo(
                        result -> mockMvc.perform(get("/movies/id=1"))
                                .andExpect(jsonPath("$.likes").value(1))
                        );
    }

    @Test
    public void testUpdateMovie() throws Exception {
        this.mockMvc.perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"title\": \"Paddington\", \"description\": \"Got Worse\", \"releaseYear\": 2012, " +
                        "\"duration\": 165, \"rating\": 9}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Got Worse"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rating").value(9));
        this.mockMvc.perform(get("/movies/id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Got Worse"))
                .andExpect(jsonPath("$.rating").value(9));
    }
}
