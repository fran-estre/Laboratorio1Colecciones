import Entidades.Movie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

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
        String data = "";
        // creo que podria aqui agregar el metodo directamente pero necesito clarificacion sobre el readline
        try {
            while ((line = reader.readLine()) != null) {
                data = data + line + "\n";
            }
            MovieReader movieReader = new MovieReader();
            return movieReader.read(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
