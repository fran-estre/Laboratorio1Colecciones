import Entidades.Color;
import Entidades.Movie;
import Entidades.MpaaRating;

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
        Movie movie = new Movie();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        try {
            while ((line = reader.readLine()) != null) {
                list = Arrays.asList(line.split(","));
                    movie.setId(Long.parseLong(list.get(0)));
                    movie.setName(list.get(1));
                    movie.getCoordinates().setX(Double.parseDouble(list.get(2)));
                    movie.getCoordinates().setY(Float.parseFloat(list.get(3)));
                    movie.setCreationDate(formatter.parse(list.get(4)));
                    movie.setOscarsCount(Long.parseLong(list.get(5)));
                    movie.setBudget(Integer.parseInt(list.get(6)));
                    movie.setTotalBoxOffice(Integer.parseInt(list.get(7)));
                    movie.setMpaaRating(getMpaaRating(list.get(8)));
                    movie.getOperator().setName(list.get(9));
                    movie.getOperator().setHeight(Long.parseLong(list.get(10)));
                    movie.getOperator().setPassportID(list.get(11));
                    movie.getOperator().setEyeColor(getEye(list.get(12)));
                    movie.getOperator().getLocation().setX(Long.parseLong(list.get(13)));
                    movie.getOperator().getLocation().setY(Float.parseFloat(list.get(14)));
                    movie.getOperator().getLocation().setZ(Float.parseFloat(list.get(15)));
                    movie.getOperator().getLocation().setName(list.get(16));
                   movies.put(movie.getId(),movie);
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
