import Entidades.Movie;

import java.util.Hashtable;

public class MovieReader {
    public Hashtable<Long, Movie> read(String data) {
        if (data == null)
            return null;
        return getMovies(data);
    }

    private Hashtable<Long, Movie> getMovies(String data) {
        Hashtable<Long, Movie> movies = new Hashtable<>();
        String row;
            Movie movie = getMovie(row);
            if (movie != null)
                movies.put(movie.getId(), movie);
        return movies;
    }
}
