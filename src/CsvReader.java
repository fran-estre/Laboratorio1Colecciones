import Entidades.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CsvReader {
    public Hashtable<Long, Movie> read(String fileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        String line;
        Hashtable<Long, Movie> movies = new Hashtable<>();
        List<String> list;
        Movie movie;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            while ((line = reader.readLine()) != null) {
                list = Arrays.asList(line.split(","));
                movie = new Movie(Long.parseLong(list.get(0)), list.get(1),
                        new Coordinates(
                                Double.parseDouble(list.get(2)),
                                Float.parseFloat(list.get(3))
                        ),
                        formatter.parse(list.get(4)),
                        Long.parseLong(list.get(5)),
                        Integer.parseInt(list.get(6)),
                        Integer.parseInt(list.get(7)),
                        getMpaaRating(list.get(8)),
                        new Person(
                                list.get(9),
                                Long.parseLong(list.get(10)),
                                list.get(11),
                                getEye(list.get(12)),
                                new Location(
                                        Long.parseLong(list.get(13)),
                                        Float.parseFloat(list.get(14)),
                                        Float.parseFloat(list.get(15)),
                                        list.get(16)
                                )
                        )
                );
                movies.put(movie.getId(), movie);
            }
            return movies;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static MpaaRating getMpaaRating(String rate) {
        List<MpaaRating> mpaaRatings = Arrays.asList(MpaaRating.values());
        MpaaRating rating = null;
        for (MpaaRating mpaaRating : mpaaRatings) {
            if (mpaaRating.name().equals(rate)) {
                rating = mpaaRating;
                break;
            }
        }
        return rating;
    }

    private static Color getEye(String eye) {
        List<Color> colors = Arrays.asList(Color.values());
        Color colorEye = null;
        for (Color color1 : colors) {
            if (color1.name().equals(eye)) {
                colorEye = color1;
                break;
            }
        }
        return colorEye;
    }
}
